package com.msa.jrg.familyservice.model;

import com.msa.jrg.core.model.MsaBaseObject;

import java.util.function.Supplier;

public class FamilyServiceObject extends MsaBaseObject implements FamilyServiceInterface {

    public String getDescription(Supplier<String> descriptionSupplier) {
        return descriptionSupplier.get();
    }
}
