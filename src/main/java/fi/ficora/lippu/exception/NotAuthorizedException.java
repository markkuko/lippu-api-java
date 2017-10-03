package fi.ficora.lippu.exception;

/**
 * This exception is thrown when client access
 * resources it has not authorized to.
 * @author markkuko
 */
public class NotAuthorizedException extends Exception{
    public NotAuthorizedException(String message) {
        super(message);
    }
}
