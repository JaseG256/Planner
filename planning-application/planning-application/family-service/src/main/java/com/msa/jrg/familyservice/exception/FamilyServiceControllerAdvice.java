package com.msa.jrg.familyservice.exception;

import com.msa.jrg.core.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class FamilyServiceControllerAdvice extends MsaControllerAdvice {

    public ResponseEntity<MsaErrorResponse> familyEventNotFound(FamilyEventNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getResourceName());
    }

    public ResponseEntity<MsaErrorResponse> placeNotFound(PlaceNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getResourceName());
    }

}
