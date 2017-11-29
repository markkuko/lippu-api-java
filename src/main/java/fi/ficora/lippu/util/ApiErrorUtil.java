package fi.ficora.lippu.util;

import fi.ficora.lippu.domain.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Locale;

public class ApiErrorUtil {

    public static ApiError generateApiError400(String message) {
        return new ApiError().statusCode(BigDecimal.valueOf(400)).message(message);
    }
    public static ResponseEntity<ApiError> generateErrorResponse400(String message) {
        return new ResponseEntity<ApiError>(ApiErrorUtil.generateApiError400(
                message), HttpStatus.BAD_REQUEST);
    }
    public static ResponseEntity<ApiError> generateErrorResponse403(String message) {
        return new ResponseEntity<ApiError>(new ApiError().statusCode(
                BigDecimal.valueOf(403)).message(message), HttpStatus.FORBIDDEN);
    }
    public static ResponseEntity<ApiError> generateErrorResponse404(String message) {
        return new ResponseEntity<ApiError>(new ApiError().statusCode(
                BigDecimal.valueOf(404)).message(message), HttpStatus.NOT_FOUND);
    }
}
