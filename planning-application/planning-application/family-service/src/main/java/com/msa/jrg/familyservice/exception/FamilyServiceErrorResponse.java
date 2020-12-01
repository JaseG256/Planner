package com.msa.jrg.familyservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FamilyServiceErrorResponse {

    private String exception;
    private HttpStatus httpStatus;
    private String errorMessage;
    private String logReference;

}
