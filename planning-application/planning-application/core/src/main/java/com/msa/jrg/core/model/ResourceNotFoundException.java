package com.msa.jrg.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends MsaBaseException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message, String resourceName, String fieldName, Object fieldValue) {
        super(message, resourceName, fieldName, fieldValue);
    }
}
