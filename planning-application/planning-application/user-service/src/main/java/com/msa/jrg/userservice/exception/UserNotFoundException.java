package com.msa.jrg.userservice.exception;

import com.msa.jrg.core.model.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message, String resourceName, String fieldName, Object fieldValue) {
        super(message, resourceName, fieldName, fieldValue);
    }
}
