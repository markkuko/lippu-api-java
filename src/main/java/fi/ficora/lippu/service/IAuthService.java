package fi.ficora.lippu.service;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.Client;
import fi.ficora.lippu.domain.Nonce;
import fi.ficora.lippu.repository.AuthRepository;
import fi.ficora.lippu.repository.ClientRepository;
import fi.ficora.lippu.repository.DataRepository;
import fi.ficora.lippu.util.KeyUtil;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;

/**
 * Handles authentication request and produces
 * @author markkuko
 */


public interface IAuthService {
    public String verifyAuthentication(String data, String cnonce, String snonce, String keyId, String alg)
            throws UnsupportedEncodingException, JoseException;


    public Nonce verifyNonce(String nonceValue, String client);


}
