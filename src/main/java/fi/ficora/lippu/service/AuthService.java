package fi.ficora.lippu.service;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.*;
import fi.ficora.lippu.exception.NotAuthorizedException;
import fi.ficora.lippu.repository.DataRepository;
import fi.ficora.lippu.repository.NonceRepository;
import fi.ficora.lippu.repository.ClientRepository;
import fi.ficora.lippu.util.KeyUtil;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.*;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Handles authentication and authorization services.
 *
 * @author markkuko
 */

@Service
public class AuthService implements IAuthService{


    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private NonceRepository nonceRepository;

    private DataRepository operatorConfiguration;
    private static final Logger log = LoggerFactory.getLogger(ClientRepository.class);

    @Autowired
    public AuthService(DataRepository operatorConfiguration) {

        this.operatorConfiguration = operatorConfiguration;

    }

    public String getClientId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public String verifyAuthentication(String data, String cnonce, String snonce, String keyId, String alg)
            throws UnsupportedEncodingException, JoseException {

        Nonce serverNonce = this.verifyNonce(snonce);
        if(serverNonce != null) {
            Client client = clientRepository.findOne(serverNonce.getClient());
            ClientKey key = findClientKey(client, keyId);
            if(key != null ) {
                try {
                    log.debug("Decoding base64 payload {}", data);
                    byte[] dataEncoded = Base64.getEncoder().encode((serverNonce.getNonce() + cnonce).getBytes("utf-8"));
                    boolean isValidSignature = checkSignature(new String(dataEncoded, Charset.forName("UTF-8")),
                            data, key.getKeyfile(), alg);
                    log.debug("Payload is {} {}  {}", isValidSignature, dataEncoded, data);
                    if (isValidSignature) {
                        String token = generateJWT(client);
                        nonceRepository.delete(serverNonce);
                        return token;
                    } else {
                        log.info("Payload signature was not valid");
                        return null;
                    }
                } catch (IllegalArgumentException e) {
                    log.debug("Exception while prosessing authentication payload {}", e.getMessage());
                    nonceRepository.delete(serverNonce.getNonce());
                    return null;
                }
            } else {
                log.info("Did not find client's ({}) key with keyId {}", client.getAccount(), keyId);
                return null;
            }
        } else {
            log.info("Did not find valid nonce", snonce);
            return null;
        }
    }

    /**
     * Generates JWT authentication token for specific client.
     * @param client Client for which token is issued for.
     * @return JWT authorization token.
     * @throws UnsupportedEncodingException
     * @throws JoseException
     */
    private String generateJWT(Client client) throws UnsupportedEncodingException, JoseException {
        // Create the claims and sign the JWT with HMAC_SHA512
        try {
            PrivateKey key = KeyUtil.getPrivateKey(operatorConfiguration.getPrivatekey());
            JwtClaims claims = new JwtClaims();
            claims.setIssuer(operatorConfiguration.getOperator());
            claims.setAudience(client.getName());
            claims.setExpirationTimeMinutesInTheFuture(Constants.JWT_EXPIRATION_TIME_MINUTES);
            claims.setGeneratedJwtId();
            claims.setIssuedAtToNow();
            claims.setNotBeforeMinutesInThePast(2);
            claims.setSubject(client.getAccount());

            JsonWebSignature jws = new JsonWebSignature();
            jws.setPayload(claims.toJson());

            jws.setKey(key);
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

            return jws.getCompactSerialization();
        } catch(Exception e) {
            log.info("Error while generating JWT: {}", e);
            return null;
        }


    }


    public String generateNonce(String account) {
        if(clientRepository.exists(account)) {
            Client client = clientRepository.findOne(account);
            log.debug("Generating nonce for client {}", client.getName());
            LocalDateTime exp = LocalDateTime.now().plusSeconds(Constants.SERVER_SIDE_NONCE_TIMEOUT_SECONDS);
            Nonce nonce = new Nonce();
            nonce.setExp(exp);
            nonce.setClient(client.getAccount());
            return nonceRepository.save(nonce).getNonce();
        } else {
            return null;
        }
    }


    private Nonce verifyNonce(String snonce) {
        Nonce nonce = nonceRepository.findOne(snonce);
        if(nonce.getExp().isAfter(LocalDateTime.now())){
            return nonce;
        } else {
            log.debug("Did not find valid nonce {}.", snonce);
            return null;
        }
    }

    public Nonce verifyNonce(String nonceValue, String client) {
        if (nonceRepository.exists(nonceValue)){
            Nonce nonce = nonceRepository.findOne(nonceValue);
            if(nonce.getExp().isAfter(LocalDateTime.now())
                    && nonce.getClient().equals(client))
                return nonce;
            else
                return null;
        } else {
            log.debug("Did not find valid nonce {}.", nonceValue);
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
        boolean returnValue = false;
        // @todo support other algorithms.
        if(!alg.equals(Constants.AUTH_SIGNATURE_RSA_SHA256)) {
            log.debug("Signature algorithm {} is not supported", alg);
            return false;
        }
        try {
            final Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(KeyUtil.getPublicKey( keyPath ) );
            signature.update( message.getBytes("utf-8" ) );

            final byte[] bytes = Base64.getDecoder().decode(sign);
            return signature.verify( bytes );

        }
        catch (FileNotFoundException e ){
            log.error( e.getMessage());
        }
        catch (IOException e ) {
            log.error("Error while validating signature: {}", e.getMessage());
        }
        catch(NoSuchAlgorithmException e ) {
            log.error("Error while validating signature: {}",  e.getMessage());
        }
        catch (InvalidKeySpecException e ) {
            log.error("Error while validating signature: {}",  e.getMessage());
        }
        catch (InvalidKeyException e ) {
            log.error("Error while validating signature: {}",  e.getMessage());
        }
        catch (SignatureException e ) {
            log.error("Error while validating signature: {}",  e.getMessage());
        }
        return returnValue;
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
        String clientId = getClientId();
        if(item == null || item.getClientId() == null || clientId == null) {
            throw new IllegalArgumentException("Nulls given as argument");
        }
        if (!item.getClientId().equals(clientId)) {
            log.warn("Client is not authorized to access item. " +
                            "Reservation item client id: {}, current client id {}"
                    , item.getClientId(), clientId);
            throw new NotAuthorizedException("Client not authorized to access item.");
        }
    }
    public void verifyAuthorization(Reservation reservation) throws NotAuthorizedException{
        String clientId = getClientId();
        if (!reservation.getClientId().equals(clientId)) {
            log.warn("Client is not authorized to access reservation. " +
                            "Reservation client id: {}, current client id {}"
                    , reservation.getClientId(), clientId);
            throw new NotAuthorizedException("Client not authorized to access reservation.");
        }
    }
}
