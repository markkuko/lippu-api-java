package fi.ficora.lippu.exception;

/**
 * This exception is thrown when client authentication
 * verification fails.
 * @author markkuko
 */
public class AuthVerificationFailed extends Exception{
    public AuthVerificationFailed(String message) {
        super(message);
    }
}
