package com.msa.jrg.familyservice.model;

import com.msa.jrg.core.model.audit.UserDateAudit;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.function.Supplier;

@Data
@Entity
@Table(name = "familyEvent")
public class FamilyEvent extends UserDateAudit implements FamilyServiceInterface {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "when")
//    @Valid
    private When when;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
//    @Valid
    private Place place;

    public FamilyEvent() {
    }

    public FamilyEvent(String title) {
        this.title = title;
    }

    public FamilyEvent(@NotBlank String title, @NotBlank When when) {
        this.title = title;
        this.when = when;
    }

    public FamilyEvent(When when, Place place) {
        this.when = when;
        this.place = place;
    }

    public FamilyEvent(String title, When when, Place place) {
        this.title = title;
        this.when = when;
        this.place = place;
    }

    @Override
    public String getDescription(Supplier<String> descriptionSupplier) {
        return descriptionSupplier.get();
    }

    @Override
    public String toString() {
        return getDescription(() ->
                String.format("Event titled: %s. Takes place on %s at %s and ends on %s at %s. " +
                        "Event is being held at %s, located at %s.", title, when.getStartDate(), when.getStartTime(),
                        when.getEndDate(), when.getEndTime(), place.getNameOfPlace(), place.getLocation()));
    }
}


