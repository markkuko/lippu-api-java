package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Auth;
import fi.ficora.lippu.domain.Nonce;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.exception.AccountNotFoundException;
import fi.ficora.lippu.exception.NotAuthorizedException;
import org.jose4j.lang.JoseException;

import java.io.UnsupportedEncodingException;

/**
 * Handles authentication and authorization services,
 * provides challenge for client authentication (nonce),
 * verification of the challenge and JWT creation and
 * authorization functions.
 *
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
     * @return Authentication for client which has JWT Authentication token,
     * expire time and clientId.
     * @throws UnsupportedEncodingException Unsupported encoding when reading
     * file.
     * @throws JoseException Exception in JWT creation.
     */
    Auth verifyAuthentication(String data, String cnonce, String snonce,
                              String keyId, String alg)
            throws UnsupportedEncodingException, JoseException;


    /**
     * Verifies that nonce is exists and has not expired.
     * @param nonceValue Nonce id for the nonce to validate.
     * @param client Client account id
     * @return Nonce if its valid or null
     */
    Nonce verifyNonce(String nonceValue, String client);

    /**
     * Retrieves client information of the current user.
     * @return Client id (principal) of the user.
     */
    String getClientId();

    /**
     * Generates server side nonce for a client and stores it.
     * @param account Client account indicator to generate nonce for.
     * @return The created @{@link Nonce}.
     */
    Nonce generateNonce(String account) throws AccountNotFoundException;

    /**
     * Verifies that current user is authorized to access
     * the resource.
     * @param item The resource to which authorization check is made.
     * @throws NotAuthorizedException If user is not allowed to access the resource,
     * {@link NotAuthorizedException}is thrown.
     */
    void verifyAuthorization(ReservationItem item) throws NotAuthorizedException;

    /**
     * Verifies that current user is authorized to access the resource.
     *
     * @param reservation The resource to which authorization check is made.
     * @throws NotAuthorizedException If user is not allowed to access the resource,
     * {@link NotAuthorizedException}is thrown.
     */
    void verifyAuthorization(Reservation reservation) throws NotAuthorizedException;
}
