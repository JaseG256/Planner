package com.msa.jrg.core.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@ControllerAdvice(annotations = RestController.class)
public class MsaControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<MsaErrorResponse> resourceNotFound(ResourceNotFoundException e) {
        return this.buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getResourceName());
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<MsaErrorResponse> appException(AppException e) {
        return this.buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<MsaErrorResponse> badRequest(BadRequestException e) {
        return this.buildErrorResponse(e, HttpStatus.BAD_REQUEST, e.getFieldName());
    }

//    @ExceptionHandler(FileStorageException.class)
//    @ResponseBody
//    public ResponseEntity<MsaErrorResponse> fileStorageException(FileStorageException e) {
//        return this.buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
//    }
//
//    @ExceptionHandler(MyFileNotFoundException.class)
//    @ResponseBody
//    public ResponseEntity<MsaErrorResponse> myFileNotFound(MyFileNotFoundException e) {
//        return this.buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getFieldName());
//    }

    protected  <E extends MsaBaseException> ResponseEntity<MsaErrorResponse> buildErrorResponse(
            E error, HttpStatus httpStatus, String logReference) {
        String message = Optional.of(error.getMessage()).orElseGet(() -> error.getFieldValue().toString());
        return new ResponseEntity<>(new MsaErrorResponse(error.getClass().getSimpleName(), httpStatus, message, logReference),
                httpStatus);
    }
}
