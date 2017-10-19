package fi.ficora.lippu.service;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.Auth;
import fi.ficora.lippu.domain.Client;
import fi.ficora.lippu.domain.ClientKey;
import fi.ficora.lippu.domain.Nonce;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.exception.AccountNotFoundException;
import fi.ficora.lippu.exception.NotAuthorizedException;
import fi.ficora.lippu.repository.ClientRepository;
import fi.ficora.lippu.repository.DataRepository;
import fi.ficora.lippu.repository.NonceRepository;

import fi.ficora.lippu.util.KeyUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Base64;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Implements authentication and authorization services.
 *
 * @author markkuko
 */

@Service
public class AuthService implements IAuthService {


    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private NonceRepository nonceRepository;

    private final DataRepository operatorConfiguration;
    private static final Logger log = LoggerFactory.getLogger(
            ClientRepository.class);

    @Autowired
    public AuthService(DataRepository operatorConfiguration) {

        this.operatorConfiguration = operatorConfiguration;

    }

    public String getClientId() {
        return (String) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
    }

    public Auth verifyAuthentication(String data, String cnonce,
                                     String snonce, String keyId,
                                     String alg)
            throws UnsupportedEncodingException, JoseException {

        Nonce serverNonce = verifyNonce(snonce);
        if (serverNonce != null) {
            Client client = this.clientRepository.findOne(serverNonce.
                    getClient());
            ClientKey key = this.findClientKey(client, keyId);
            if (key != null) {
                try {
                    AuthService.log.debug("Decoding base64 payload {}", data);
                    byte[] dataEncoded = Base64.getEncoder().encode(
                            (serverNonce.getNonce() + cnonce)
                                    .getBytes("utf-8"));
                    boolean isValidSignature = AuthService.checkSignature(
                            new String(dataEncoded, Charset.forName("UTF-8")),
                            data, key.getKeyfile(), alg);
                    AuthService.log.debug("Payload is {} {}  {}", isValidSignature, dataEncoded, data);
                    if (isValidSignature) {
                        Auth auth = this.generateJWT(client);
                        this.nonceRepository.delete(serverNonce);
                        return auth;
                    } else {
                        AuthService.log.info("Payload signature was not valid");
                        return null;
                    }
                } catch (IllegalArgumentException e) {
                    AuthService.log.debug("Exception while processing authentication payload {}", e.getMessage());
                    this.nonceRepository.delete(serverNonce.getNonce());
                    return null;
                }
            } else {
                AuthService.log.info("Did not find client's ({}) key with keyId {}", client.getAccount(), keyId);
                return null;
            }
        } else {
            AuthService.log.info("Did not find valid nonce", snonce);
            return null;
        }
    }

    /**
     * Generates JWT authentication token for specific client.
     * @param client Client for which token is issued for.
     * @return JWT authorization token.
     * @throws JoseException JWT Jose Exception
     */
    private Auth generateJWT(Client client) throws JoseException {
        // Create the claims and sign the JWT with HMAC_SHA512
        try {
            PrivateKey key = KeyUtil.getPrivateKey(this.operatorConfiguration.getPrivateKey());
            JwtClaims claims = new JwtClaims();
            claims.setIssuer(this.operatorConfiguration.getOperator());
            claims.setAudience(client.getAccount());
            OffsetDateTime exp = OffsetDateTime.now().plusMinutes(
                    Constants.JWT_EXPIRATION_TIME_MINUTES);
            claims.setExpirationTime(NumericDate.fromSeconds(exp.toEpochSecond()));

            claims.setGeneratedJwtId();
            claims.setIssuedAtToNow();
            claims.setNotBeforeMinutesInThePast(2);
            claims.setSubject(client.getAccount());

            JsonWebSignature jws = new JsonWebSignature();
            jws.setPayload(claims.toJson());

            jws.setKey(key);
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
            Auth authentication =  new Auth();
            authentication.setToken(jws.getCompactSerialization());
            authentication.setExpires(exp);
            authentication.setClientId(client.getAccount());

            return authentication;
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            AuthService.log.info("Error while generating JWT: {}", e);
            return null;
        }

    }


    public Nonce generateNonce(String account) throws AccountNotFoundException {
        if(this.clientRepository.exists(account)) {
            Client client = this.clientRepository.findOne(account);
            AuthService.log.debug("Generating nonce for client {}", client.getName());
            LocalDateTime exp = LocalDateTime.now().plusSeconds(Constants.SERVER_SIDE_NONCE_TIMEOUT_SECONDS);
            Nonce nonce = new Nonce();
            nonce.setExp(exp);
            nonce.setClient(client.getAccount());
            return this.nonceRepository.save(nonce);
        } else {
            AuthService.log.info("Did not find account for account: {}", account);
            throw new AccountNotFoundException("Account not found");
        }
    }


    private Nonce verifyNonce(String snonce) {
        Nonce nonce = this.nonceRepository.findOne(snonce);
        if(nonce.getExp().isAfter(LocalDateTime.now())){
            return nonce;
        } else {
            AuthService.log.info("Did not find valid nonce {}.", snonce);
            return null;
        }
    }

    public Nonce verifyNonce(String nonceValue, String client) {
        if (this.nonceRepository.exists(nonceValue)){
            Nonce nonce = this.nonceRepository.findOne(nonceValue);
            if(nonce.getExp().isAfter(LocalDateTime.now())
                    && nonce.getClient().equals(client))
                return nonce;
            else
                return null;
        } else {
            AuthService.log.info("Did not find valid nonce {}.", nonceValue);
            return null;
        }
    }

    /**
     * Verifies given signature for the message.
     * @param message Message for which the signature is calculated
     * @param sign Signature to verify
     * @param keyPath Public key used to verify signature
     * @param alg Used algorithm in the. Currently RSA with SHA 256 is supported.
     * @return Boolean depending if the verification was valid or not.
     */
    public static boolean checkSignature(String message, String sign, String keyPath, String alg )
    {
        // @todo support other algorithms.
        if(!alg.equals(Constants.AUTH_SIGNATURE_RSA_SHA256)) {
            AuthService.log.debug("Signature algorithm {} is not supported", alg);
            return false;
        }
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(KeyUtil.getPublicKey( keyPath ) );
            signature.update( message.getBytes("utf-8" ) );

            byte[] bytes = Base64.getDecoder().decode(sign);
            return signature.verify( bytes );

        }
        catch (FileNotFoundException e ){
            AuthService.log.error( e.getMessage());
        }
        catch (IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException | SignatureException e ) {
            AuthService.log.error("Error while validating signature: {}", e.getMessage());
        }
        return false;
    }
    private ClientKey findClientKey(Client client, String keyId) {
        for(ClientKey key: client.getKeys()) {
            if(key.getPubKeyId().equals(keyId)) {
                return key;
            }
        }
        return null;
    }

    public void verifyAuthorization(ReservationItem item) throws NotAuthorizedException{
        String clientId = this.getClientId();
        if(item == null || item.getClientId() == null || clientId == null) {
            throw new IllegalArgumentException("Nulls given as argument");
        }
        if (!item.getClientId().equals(clientId)) {
            AuthService.log.warn("Client is not authorized to access item. " +
                            "Reservation item client id: {}, current client id {}"
                    , item.getClientId(), clientId);
            throw new NotAuthorizedException("Client not authorized to access item.");
        }
    }
    public void verifyAuthorization(Reservation reservation) throws NotAuthorizedException{
        String clientId = this.getClientId();
        if (!reservation.getClientId().equals(clientId)) {
            AuthService.log.warn("Client is not authorized to access reservation. " +
                            "Reservation client id: {}, current client id {}"
                    , reservation.getClientId(), clientId);
            throw new NotAuthorizedException("Client not authorized to access reservation.");
        }
    }
}
