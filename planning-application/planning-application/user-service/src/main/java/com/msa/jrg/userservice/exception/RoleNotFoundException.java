package com.msa.jrg.userservice.exception;

import com.msa.jrg.core.model.MsaBaseException;
import com.msa.jrg.core.model.ResourceNotFoundException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException() {
    }

    public RoleNotFoundException(String message, String resourceName, String fieldName, Object fieldValue) {
        super(message, resourceName, fieldName, fieldValue);
    }
}
