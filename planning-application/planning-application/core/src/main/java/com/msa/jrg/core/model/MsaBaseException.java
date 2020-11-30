package com.msa.jrg.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class MsaBaseException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private String exceptionMessage;
    private Object fieldValue;

    public MsaBaseException() {
    }

    public MsaBaseException(String message) {
        super(message);
    }

    public MsaBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public MsaBaseException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public MsaBaseException(String message, String resourceName, String fieldName, Object fieldValue) {
        super(message);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}