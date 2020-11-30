package com.msa.jrg.familyservice.payload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FamilyEventResponse {

    private String title;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime startTime;

    private String nameOfPlace;

    public FamilyEventResponse() {
    }

    public FamilyEventResponse(String title, LocalDate startDate, @Nullable LocalTime startTime, String nameOfPlace) {
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.nameOfPlace = nameOfPlace;
    }

}
