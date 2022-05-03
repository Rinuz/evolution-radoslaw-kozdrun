package pl.kozdrun.evolution.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.kozdrun.evolution.exception.RestException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestException> handleRestException(RestException restException) {
        Map<String, Object> responseValuesMap = new HashMap<>();
        responseValuesMap.put("status", restException.getHttpStatus().getReasonPhrase());
        responseValuesMap.put("code", restException.getHttpStatus().value());
        responseValuesMap.put("timestamp", LocalDateTime.now());
        responseValuesMap.put("message", restException.getMessage());
        return new ResponseEntity(responseValuesMap, restException.getHttpStatus());
    }
}