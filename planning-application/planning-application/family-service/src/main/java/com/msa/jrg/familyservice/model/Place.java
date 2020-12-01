package com.msa.jrg.familyservice.model;

import com.msa.jrg.core.model.audit.UserDateAudit;
import lombok.Data;

import javax.persistence.*;
import java.util.function.Supplier;

@Data
@Entity
@Table(name = "place")
public class Place extends UserDateAudit implements FamilyServiceInterface {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nameOfPlace;

    @Column
    private String location;

    @Lob
    @Column
    private Address address;

    public Place() {
    }

    public Place(String nameOfPlace) {
        this.nameOfPlace = nameOfPlace;
    }

    public Place(Address address) {
        this.address = address;
    }

    public Place(String nameOfPlace, String location) {
        this.nameOfPlace = nameOfPlace;
        this.location = location;
    }

    public Place(String nameOfPlace, Address address) {
        this.nameOfPlace = nameOfPlace;
        this.address = address;
    }

    public Place(String nameOfPlace, String location, Address address) {
        this.nameOfPlace = nameOfPlace;
        this.location = location;
        this.address = address;
    }

    @Override
    public String getDescription(Supplier<String> descriptionSupplier) {
        return descriptionSupplier.get();
    }
}
