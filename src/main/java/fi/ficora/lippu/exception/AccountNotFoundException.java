package fi.ficora.lippu.exception;

/**
 * This exception is thrown when client tries
 * to authenticate with account that is not found.
 * @author markkuko
 */
public class AccountNotFoundException extends Exception{
    public AccountNotFoundException(String message) {
        super(message);
    }
}
