package com.msa.jrg.familyservice.payload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FamilyEventRequest {

    public FamilyEventRequest() {
    }

    public FamilyEventRequest(String title, LocalDate startDate, LocalTime startTime, String nameOfPlace) {
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.nameOfPlace = nameOfPlace;
    }

    public FamilyEventRequest(
            String title,  LocalDate startDate, LocalDate endDate,
            LocalTime startTime, LocalTime endTime, String nameOfPlace,
            String location, String streetNumber, String city, String state, String country, int zipCode
    ) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.nameOfPlace = nameOfPlace;
        this.location = location;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    @NotBlank
    private String title;

    @NotBlank
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime startTime;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime endTime;

    @NotBlank
    private String nameOfPlace;

    private String location;
    private String streetNumber;
    private String city;
    private String state;
    private String country;
    private int zipCode;

}