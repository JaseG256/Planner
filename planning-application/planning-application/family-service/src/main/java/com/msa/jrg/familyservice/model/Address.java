package com.msa.jrg.familyservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        Comparator<Address> addressComparator = (Address a1, Address a2) -> {
            int compared = -1;
            int result = 0;
            if (a1.getStreetNumber() != null && a2.getStreetNumber() != null)
                result = a1.getStreetNumber().compareTo(a2.getStreetNumber());
            int cityResult = a1.getCity().compareTo(a2.getCity());
            int stateResult = a1.getState().compareTo(a2.getState());
            int countryResult = a1.getCountry().compareTo(a2.getCountry());
            if (result + cityResult + stateResult + countryResult == 4) {
                compared = 0;
            } else if (a1.getState().equals(a2.getState())) {
                compared =  a1.getCity().compareTo(a2.getCity());
            } else if (a1.getState().equals(a2.getState()) && a1.getCity().equals(a2.getCity())) {
                compared =  a1.getStreetNumber().compareTo(a2.getStreetNumber());
            }
            return compared;
        };
        return Objects.compare(this, address, addressComparator) == 0;
    }

}
