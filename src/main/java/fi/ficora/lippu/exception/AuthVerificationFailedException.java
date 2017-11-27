package fi.ficora.lippu.exception;

/**
 * This exception is thrown when client authentication
 * verification fails.
 * @author markkuko
 */
public class AuthVerificationFailedException extends Exception{
    public AuthVerificationFailedException(String message) {
        super(message);
    }
}
