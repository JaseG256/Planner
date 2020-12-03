package com.msa.jrg.familyservice.model;

import com.msa.jrg.core.model.audit.UserDateAudit;
import com.msa.jrg.familyservice.payload.FamilyEventRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "familyEvent")
public class FamilyEvent extends UserDateAudit implements FamilyServiceInterface {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
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

    public FamilyEvent(String title, When when) {
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

    @Builder
    public static FamilyEvent buildFamilyEvent(String title, When when, Place place) {
        FamilyEvent familyEvent = new FamilyEvent();
        familyEvent.title = title;
        familyEvent.when = when;
        familyEvent.place = place;
        return familyEvent;
    }

    public FamilyEvent fromFamilyEventRequest(@NonNull FamilyEventRequest eventRequest) {
        return new FamilyEvent(eventRequest.getTitle(), new When(eventRequest.getStartDate(), eventRequest.getEndDate(),
                eventRequest.getStartTime(), eventRequest.getEndTime()), new Place(eventRequest.getNameOfPlace(),
                eventRequest.getLocation(), new Address(eventRequest.getStreetNumber(), eventRequest.getCity(),
                eventRequest.getState(), eventRequest.getCountry(), eventRequest.getZipCode())));
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


