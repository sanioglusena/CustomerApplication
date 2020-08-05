package com.delivery.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomerServiceException extends RuntimeException {

    public CustomerServiceException(String message) {
        super(message);
    }
}
