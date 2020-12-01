package com.msa.jrg.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends MsaBaseException {

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, String resourceName, String fieldName, Object fieldValue) {
        super(message, resourceName, fieldName, fieldValue);
    }
}
