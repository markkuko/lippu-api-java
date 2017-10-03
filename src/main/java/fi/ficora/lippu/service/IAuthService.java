package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Nonce;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.exception.NotAuthorizedException;
import org.jose4j.lang.JoseException;

import java.io.UnsupportedEncodingException;

/**
 * Handles authentication and authorization services,
 * including implementing authentication for clients.
 * @author markkuko
 */


public interface IAuthService {
    /**
     * Verifies message signature of using clients public key.
     *
     * @param data Client's signature data
     * @param cnonce Client side nonce
     * @param snonce Server side nonce
     * @param keyId Client key id
     * @param alg Algorithm client used for signature
     * @return JWT Authentication token for client.
     * @throws UnsupportedEncodingException
     * @throws JoseException
     */
    public String verifyAuthentication(String data, String cnonce, String snonce, String keyId, String alg)
            throws UnsupportedEncodingException, JoseException;


    /**
     * Verifies that nonce is exists and has not expired.
     * @param nonceValue Nonce id for the nonce to validate.
     * @param client Client account id
     * @return Nonce is its valid or null
     */
    public Nonce verifyNonce(String nonceValue, String client);

    /**
     * Retrieves client information from the security context of the current user.
     * @return Client id (principal) of the user.
     */
    public String getClientId();

    /**
     * Generates serverside nonce for a client and stores it.
     * @param account Client account indicator to generate nonce for.
     * @return Nonce value
     */
    public String generateNonce(String account);

    /**
     * Verifies that current user is authorized to access
     * the resource.
     * @param item The resource to which authorization check is made.
     * @throws NotAuthorizedException If user is not allowed to access the resource,
     * {@link NotAuthorizedException}is thrown.
     */
    public void verifyAuthorization(ReservationItem item) throws NotAuthorizedException;

    /**
     * Verifies that current user is authorized to access the resource.
     *
     * @param reservation The resource to which authorization check is made.
     * @throws NotAuthorizedException If user is not allowed to access the resource,
     * {@link NotAuthorizedException}is thrown.
     */
    public void verifyAuthorization(Reservation reservation) throws NotAuthorizedException;
}
