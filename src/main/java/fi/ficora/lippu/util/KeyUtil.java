package fi.ficora.lippu.util;

import org.bouncycastle.util.io.pem.PemReader;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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
        FileInputStream fileStream = null;
        PemReader reader = null;
        try {
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            fileStream = new FileInputStream(keyPath);
            reader = new PemReader(new InputStreamReader(
                    fileStream, Charset.forName("UTF-8")));
            final byte[] pubKey = reader.readPemObject().getContent();
            reader.close();
            fileStream.close();
            final X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(pubKey);

            return keyFactory.generatePublic(publicKeySpec);
        } finally {
            if(reader != null){
                reader.close();
            }
            if(fileStream != null){
                fileStream.close();
            }

        }
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
