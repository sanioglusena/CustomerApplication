package com.delivery.customer.advice;

import com.delivery.customer.exception.CustomerServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Handles CustomerServiceExceptions
     *
     * @param exception
     * @param request
     * @return Object with request path, error message and timestamp information
     */
    @ExceptionHandler(CustomerServiceException.class)
    protected ResponseEntity<Object> handleException(RuntimeException exception, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());
        body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        log.error("CustomerServiceException in: " + ((ServletWebRequest) request).getRequest().getRequestURI() + " message: " + exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return Object which includes timestamp, request path and validation error messages
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
        body.put("errors", errors);

        log.error("Validation error in : " + ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
