package fi.ficora.lippu.service;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.Client;
import fi.ficora.lippu.domain.Nonce;
import fi.ficora.lippu.repository.DataRepository;
import fi.ficora.lippu.repository.AuthRepository;
import fi.ficora.lippu.repository.ClientRepository;
import fi.ficora.lippu.util.KeyUtil;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

/**
 * Handles authentication request and produces
 * @author markkuko
 */

@Service
public class AuthService implements IAuthService{

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AuthRepository authRepository;

    private DataRepository operatorConfiguration;
    private static final Logger log = LoggerFactory.getLogger(ClientRepository.class);

    @Autowired
    public AuthService(DataRepository operatorConfiguration) {

        this.operatorConfiguration = operatorConfiguration;

    }

    public String verifyAuthentication(String data, String cnonce, String keyId, String alg)
            throws UnsupportedEncodingException, JoseException {

        List<Client> clients = clientRepository.findDistinctClientByPubKeyId(keyId);
        if(clients.size() == 1) {

            Client client = clients.get(0);
            Nonce serverNonce = this.verifyNonce(client.getAccount());
            try {
                log.debug("Decoding base64 payload {}", data);
                byte[] dataEncoded = Base64.getEncoder().encode((serverNonce.getNonce() + cnonce).getBytes("utf-8"));
                boolean isValidSignature = checkSignature(new String(dataEncoded),
                        data, client.getKeyfile(), alg);
                log.info("Payload is {} {}  {}", isValidSignature, dataEncoded, data);
                if (isValidSignature) {
                    String token = generateJWT(client);
                    authRepository.delete(serverNonce);
                    return token;
                } else {
                    log.info("Payload signature was not valid");
                    return null;
                }
            } catch (IllegalArgumentException e) {
                log.debug("Exception while prosessing authentication payload {}", e.getMessage());
                authRepository.delete(serverNonce.getNonce());
                return null;
            }
        } else {
            log.info("Found {} clients with pubKeyId {}",clients.size() , keyId);
            return null;
        }
    }

    /**
     * Generates JWT authentication token for specific client.
     * @// TODO: 9/22/17 sign the token with RSA key
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

    /**
     * Generates controller side nonce for a client and stores it.
     * @param account Client account indicator to generate nonce for.
     * @return Nonce value
     */
    public String generateNonce(String account) {
        if(clientRepository.exists(account)) {
            Client client = clientRepository.findOne(account);
            log.debug("Generating nonce for client {}", client.getName());
            LocalDateTime exp = LocalDateTime.now().plusSeconds(Constants.SERVER_SIDE_NONCE_TIMEOUT_SECONDS);
            Nonce nonce = new Nonce();
            nonce.setExp(exp);
            nonce.setClient(client.getAccount());
            return authRepository.save(nonce).getNonce();
        } else {
            return null;
        }
    }

    /**
     * Verifies that nonce is exists and has not expired.
     * @param client Find nonce by client
     * @return Is nonce valid.
     */

    private Nonce verifyNonce(String client) {
        Nonce nonce = authRepository.findOneNonceByClient(client);
        if(nonce.getExp().isAfter(LocalDateTime.now())){
            return nonce;
        } else {
            log.debug("Did not find valid nonce {}.", client);
            return null;
        }
    }
    /**
     * Verifies that nonce is exists and has not expired.
     * @param nonceValue Server side nonce to validate.
     * @return Is nonce valid.
     */

    public boolean verifyNonce(String nonceValue, String client) {
        if (authRepository.exists(nonceValue)){
            Nonce nonce = authRepository.findOne(nonceValue);
            if(nonce.getExp().isAfter(LocalDateTime.now())
                    && nonce.getClient().equals(client))
                return true;
            else
                return false;
        } else {
            log.debug("Did not find valid nonce {}.", nonceValue);
            return false;
        }
    }

    /**
     * Verifies given signature for the message.
     * @param message Message for which the signature is calculated
     * @param sign Signature to verify
     * @param keyPath Public key used to verify signature
     * @param alg Used algorithm in the
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

}
