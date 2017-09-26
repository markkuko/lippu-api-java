package fi.ficora.lippu.domain;


/**
 * Custom error messages in API responses.
 *
 * @author markkuko
 * @since 26.9.2017
 */
public class ApiError {
    private int statusCode;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
