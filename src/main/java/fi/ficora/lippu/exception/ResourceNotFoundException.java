package fi.ficora.lippu.exception;

/**
 * This exception is thrown when resource
 * was not found with given id in repositories.
 * @author markkuko
 */
public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
