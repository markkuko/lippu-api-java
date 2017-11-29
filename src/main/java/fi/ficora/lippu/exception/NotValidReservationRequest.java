package fi.ficora.lippu.exception;

/**
 * This exception is thrown when request
 * reservation validation fails.
 * @author markkuko
 */
public class NotValidReservationRequest extends Exception{
    public NotValidReservationRequest(String message) {
        super(message);
    }
}
