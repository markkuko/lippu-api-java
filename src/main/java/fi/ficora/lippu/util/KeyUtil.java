package fi.ficora.lippu.util;

import fi.ficora.lippu.config.Constants;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Util methods for creating private and public keys
 * @author markkuko
 */
public class KeyUtil {
    /**
     * Get RSA public key from PEM formatted key file.
     * @param keyPath Path to the key file.
     * @return Public key part of the key.
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String keyPath )
            throws NoSuchAlgorithmException, IOException, InvalidKeySpecException
    {
        final KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
        final PemReader reader = new PemReader( new FileReader( keyPath ) );
        final byte[] pubKey = reader.readPemObject(  ).getContent(  );
        final X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec( pubKey );

        return keyFactory.generatePublic( publicKeySpec );
    }
    /**
     * Get RSA private key from DER formatted key file using {@link PKCS8EncodedKeySpec}.
     * @param keyPath Path to the key file.
     * @return Public key part of the key.
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String keyPath )
            throws NoSuchAlgorithmException, IOException, InvalidKeySpecException
    {
        final KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
        final byte[] pubKey = Files.readAllBytes(Paths.get(keyPath));
        final PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec( pubKey );

        return keyFactory.generatePrivate( privateKeySpec );
    }


}
