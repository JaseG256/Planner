package com.msa.jrg.familyservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.function.Supplier;

@NoArgsConstructor
@Data
public class Address extends FamilyServiceObject implements Serializable {

    private String streetNumber;
    private String city;
    private String state;
    private String country;
    private int zipCode;

    public Address(String country) {
        this.country = country;
    }

    public Address( String streetNumber, String city,
                    String state, String country, int zipCode) {
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.country = (country != null) ? country : "United States";
        this.zipCode = zipCode;
    }

    public Address( String country, String streetNumber, String city, String state) {
        this.country = country;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
    }

    public Address( String country, String city, String state) {
        this.country = country;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return getDescription(() ->
            String.format(
                    "Address - streetNumber: %s, city: %s, state: %s, country: %s, zipCode: %s",
                    streetNumber, city, state, country, zipCode)
        );
    }
}
