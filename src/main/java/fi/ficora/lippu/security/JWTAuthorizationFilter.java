package fi.ficora.lippu.security;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.repository.DataRepository;
import fi.ficora.lippu.util.KeyUtil;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
/**
 * JWTAuthorizationFilter validates that request has valid
 * token in the header. Sets the @{@link SecurityContextHolder}
 * for valid requests.
 *
 * @author markkuko
 */
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);


    @Autowired
    DataRepository dataRepository;

    public JWTAuthorizationFilter() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authToken = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (authToken == null) {
            log.info("Did not find authentication token, continue request processing.");
            filterChain.doFilter(request, response);
            return;
        }
        // Validate the value in authToken and set security context
        UsernamePasswordAuthenticationToken auth = authenticate(request);
        if(auth == null) {
            log.info("Authentication is null, continue request processing.");
            filterChain.doFilter(request, response);
        } else {
            log.info("Authentication valid for {}, continue request processing.",
                    auth.getPrincipal());
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        }


    }

    /**
     * Validates that the request has authorization token and validates
     * the JWT-token.
     * @param request Http request to be authorized.
     * @return Authentication token.
     */
    private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {
        try {
            String token = request.getHeader(Constants.AUTHORIZATION_HEADER);
            PublicKey key = KeyUtil.getPublicKey(dataRepository.getPublicKey());
            PrivateKey privateKey = KeyUtil.getPrivateKey(dataRepository.getPrivateKey());
            AlgorithmConstraints jwsAlgConstraints = new AlgorithmConstraints(
                    AlgorithmConstraints.ConstraintType.WHITELIST,
                    AlgorithmIdentifiers.RSA_USING_SHA256);

            AlgorithmConstraints jweAlgConstraints = new AlgorithmConstraints(
                    AlgorithmConstraints.ConstraintType.WHITELIST,
                    KeyManagementAlgorithmIdentifiers.RSA_OAEP_256);

            AlgorithmConstraints jweEncConstraints = new AlgorithmConstraints(
                    AlgorithmConstraints.ConstraintType.WHITELIST,
                    ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
            if (token != null) {
                log.trace("Verifying token: {}", token);
                //Key key = new HmacKey(dataRepository.getSecret().getBytes("UTF-8"));
                JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                        .setRequireExpirationTime()
                        .setAllowedClockSkewInSeconds(20)
                        .setRequireSubject()
                        .setSkipDefaultAudienceValidation()
                        .setExpectedIssuer(dataRepository.getOperator())
                        .setDecryptionKey(privateKey)
                        .setVerificationKey(key)
                        .setJwsAlgorithmConstraints(jwsAlgConstraints)
                        .setJweAlgorithmConstraints(jweAlgConstraints)
                        .setJweContentEncryptionAlgorithmConstraints(jweEncConstraints)
                        .build();

                //  Validate the JWT and process it to the Claims
                JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
                log.trace("JWT validation succeeded! Claims: {}", jwtClaims);
                return new UsernamePasswordAuthenticationToken(jwtClaims.getSubject(),
                        null, new ArrayList<>());
            } else {
                return null;
            }
        }catch (InvalidJwtException e) {
            log.info("Invalid JWT token: {} ",e.getMessage());

            return null;
        } catch (UnsupportedEncodingException e) {
            log.info("UnsupportedEncodingException: {} ",e.getMessage());
            return null;
        } catch (MalformedClaimException e) {
            log.info("MalformedClaimException: {} ", e.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e) {
            log.info("NoSuchAlgorithmException: {} ", e.getMessage());
            return null;
        } catch (IOException e) {
            log.info("IOException: {} ", e.getMessage());
            return null;
        } catch (InvalidKeySpecException e) {
            log.info("InvalidKeySpecException: {} ", e.getMessage());
            return null;
        }
    }


}