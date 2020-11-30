package com.msa.jrg.familyservice.model;

import java.util.function.Supplier;

@FunctionalInterface
public interface FamilyServiceInterface {

    String getDescription(Supplier<String> descriptionSupplier);
}
