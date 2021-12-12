package com.onurcelik.readingisgood.core.exception.handler;

import com.onurcelik.readingisgood.core.exception.BusinessException;
import com.onurcelik.readingisgood.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public final class CustomExceptionHandler {

    private static final String DATA = "data";
    private static final String SUCCESS = "success";
    private static final String HTTP_STATUS = "httpStatus";
    private static final String ERROR_MESSAGE = "errorMessage";

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(SUCCESS, false);
        body.put(HTTP_STATUS, HttpStatus.NOT_FOUND);
        body.put(ERROR_MESSAGE, ex.getMessage());
        body.put(DATA, null);

        log.error(body.toString());
        return ResponseEntity.ok().body(body);
    }

    @ExceptionHandler({SystemException.class})
    public ResponseEntity<?> handleSystemException(SystemException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(SUCCESS, false);
        body.put(HTTP_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        body.put(ERROR_MESSAGE, ex.getMessage());
        body.put(DATA, null);

        log.error(body.toString());
        return ResponseEntity.ok().body(body);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(SUCCESS, false);
        body.put(HTTP_STATUS, HttpStatus.UNAUTHORIZED);
        body.put(ERROR_MESSAGE, ex.getMessage());
        body.put(DATA, null);

        log.error(body.toString());
        return ResponseEntity.ok().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(SUCCESS, false);
        body.put(HTTP_STATUS, HttpStatus.BAD_REQUEST);
        body.put(ERROR_MESSAGE, errors);
        body.put(DATA, null);
        return ResponseEntity.ok().body(body);
    }
}
