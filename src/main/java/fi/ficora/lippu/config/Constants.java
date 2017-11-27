package fi.ficora.lippu.config;

public class Constants {
    public static final String AUTHORIZATION_HEADER = "X-Authorization";

    public static final int RESULT_CODE_SUCCESS = 0;
    public static final int RESULT_CODE_NOT_FOUND = 10;
    public static final int RESULT_CODE_FORBIDDEN = 11;
    public static final long SERVER_SIDE_NONCE_TIMEOUT_SECONDS = 60;
    public static final long JWT_EXPIRATION_TIME_MINUTES = 100;

    public static final String AUTH_SIGNATURE_RSA_SHA256 = "RSA+SHA256";

    public static final String TOKEN_SIGNATURE_SHA256RSA = "SHA256withRSA";

    public static final String COORDINATE_PRECISION = "#,##0.0";

    public static final int RESERVATION_AVAILABILITY_MINUTES = 3;

    public static final int SCHEDULED_REMOVE_BUFFER_IN_MINUTES = 1;

    public static final int SCHEDULED_REMOVE_BEFORE_IN_DAYS = 30;

    public static final int TICKET_VALID_PERIOD_IN_MINUTES = 45;

}
