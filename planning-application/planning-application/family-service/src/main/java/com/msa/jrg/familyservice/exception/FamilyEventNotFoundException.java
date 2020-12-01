package com.msa.jrg.familyservice.exception;

import com.msa.jrg.core.model.ResourceNotFoundException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class FamilyEventNotFoundException extends ResourceNotFoundException {

    public FamilyEventNotFoundException(String message, String resourceName, String fieldName, Object fieldValue) {
        super(message, resourceName, fieldName, fieldValue);
    }
}
