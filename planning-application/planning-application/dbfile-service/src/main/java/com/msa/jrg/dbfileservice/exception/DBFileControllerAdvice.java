package com.msa.jrg.dbfileservice.exception;

import com.msa.jrg.core.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@ControllerAdvice(annotations = RestController.class)
public class DBFileControllerAdvice extends MsaControllerAdvice {

    @ExceptionHandler(MyFileNotFoundException.class)
    public ResponseEntity<MsaErrorResponse> myFileNotFoundNotFound(MyFileNotFoundException e) {
        return this.buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getExceptionMessage());
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<MsaErrorResponse> fileStorage(FileStorageException e) {
        return this.buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getExceptionMessage());
    }

    @Override
    protected <E extends MsaBaseException> ResponseEntity<MsaErrorResponse> buildErrorResponse(
            E error, HttpStatus httpStatus, String logReference) {
        String message = Optional.of(error.getMessage()).orElseGet(() -> error.getFieldValue().toString());
        MsaErrorResponse errorResponse = new DBFileErrorResponse(
                error.getClass().getSimpleName(), httpStatus, message, logReference);
        return new ResponseEntity<>(errorResponse,
                httpStatus);
    }
}
