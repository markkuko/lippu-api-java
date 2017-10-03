package fi.ficora.lippu.config;

public class Constants {
    public static final String AUTHORIZATION_HEADER = "X-Authorization";

    public static final int RESULTCODE_SUCCESS = 0;
    public static final int RESULTCODE_NOT_FOUND = 10;
    public static final int RESULTCODE_FORBIDDEN = 11;
    public static final long SERVER_SIDE_NONCE_TIMEOUT_SECONDS = 500;
    public static final long JWT_EXPIRATION_TIME_MINUTES = 100;

    public static final String AUTH_SIGNATURE_RSA_SHA256 = "RSA+SHA256";

    public static final String COORDINATE_PRECISION = "#,##0.0";

    public static final int RESERVATION_AVAILABILITY_MINUTES = 15;

    public static final int TICKET_VALID_PERIOD_IN_MINUTES = 45;

}
