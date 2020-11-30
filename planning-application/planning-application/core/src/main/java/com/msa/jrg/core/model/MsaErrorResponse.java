package com.msa.jrg.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsaErrorResponse {

    private String exceptionName;
    private HttpStatus httpStatus;
    private String errorMessage;
    private String logReference;

}
