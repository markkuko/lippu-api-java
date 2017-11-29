package fi.ficora.lippu.exception;

/**
 * This exception is thrown when timetable
 * was not found for a product.
 * @author markkuko
 */
public class TimetableNotFoundException extends Exception{
    public TimetableNotFoundException(String message) {
        super(message);
    }
}
