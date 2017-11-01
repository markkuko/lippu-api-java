package fi.ficora.lippu.util;

import fi.ficora.lippu.domain.model.ApiError;

import java.math.BigDecimal;
import java.util.Locale;

public class ApiErrorUtil {

    public static ApiError generateApiError400(String message) {
        return new ApiError().statusCode(BigDecimal.valueOf(400)).message(message);
    }
}
