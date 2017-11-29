package fi.ficora.lippu.exception;

/**
 * This exception is thrown when {@link fi.ficora.lippu.domain.Product}
 * was not found with given id in repositories.
 * @author markkuko
 */
public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
