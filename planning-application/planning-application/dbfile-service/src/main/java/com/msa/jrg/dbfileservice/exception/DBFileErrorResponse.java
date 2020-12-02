package com.msa.jrg.dbfileservice.exception;

import com.msa.jrg.core.model.MsaErrorResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DBFileErrorResponse extends MsaErrorResponse



{
    public DBFileErrorResponse(String exceptionName, HttpStatus httpStatus, String errorMessage, String logReference) {
        super(exceptionName, httpStatus, errorMessage, logReference);
    }

    public DBFileErrorResponse() {
    }
}
