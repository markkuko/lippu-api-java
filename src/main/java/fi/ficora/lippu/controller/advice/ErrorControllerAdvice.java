package fi.ficora.lippu.controller.advice;

import fi.ficora.lippu.domain.model.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * RestController to produce JSON error responses for bad requests (http statuscode 400).
 *
 * @author markkuko
 * @since 26.9.2017
 */
@RestControllerAdvice
public class ErrorControllerAdvice {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class,
            HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiError> badRequest(HttpServletRequest req, Exception exception) {
        ApiError error = new ApiError();
        error.setStatusCode(new BigDecimal(400));
        error.setMessage(messageSource.getMessage("http.error.message.400",null, Locale.ENGLISH));
        return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);
    }
}
