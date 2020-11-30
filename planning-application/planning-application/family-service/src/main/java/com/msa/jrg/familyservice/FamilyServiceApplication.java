package com.msa.jrg.familyservice;

import com.msa.jrg.core.model.AppException;
import com.msa.jrg.core.model.MsaBaseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FamilyServiceApplication {

    private MsaBaseException exception = new AppException();

    public static void main(String[] args) {
        SpringApplication.run(FamilyServiceApplication.class, args);
    }

}
