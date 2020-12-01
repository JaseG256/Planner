package com.msa.jrg.familyservice.service;

import com.msa.jrg.familyservice.config.FamilyEventPropertiesConfig;
import com.msa.jrg.familyservice.config.ServiceUnitTestConfig;
import com.msa.jrg.familyservice.model.Address;
import com.msa.jrg.familyservice.model.FamilyEvent;
import com.msa.jrg.familyservice.model.Place;
import com.msa.jrg.familyservice.model.When;
import com.msa.jrg.familyservice.repository.FamilyEventRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceUnitTestConfig.class)
public class FamilyEventServiceImplTest {

    @Autowired
    FamilyEventService eventService;

    @Autowired
    @Qualifier("placeTwo")
    PlaceService placeService;

    @Autowired
    FamilyEventRepository familyEventRepository;

    @Autowired
    FamilyEventPropertiesConfig propertiesConfig;

    FamilyEvent summerEvent, fallEvent, placeEvent;
    When summerWhen, fallWhen;
    LocalDate summerStartDate, summerEndDate, fallStartDate, fallEndDate;
    LocalTime summerStartTime, fallStartTime, fallEndTime;
    Place summerPlace, fallPlace, eventPlace;
    Address addressOne, addressTwo, addressThree;
    String summerTitle, fallTitle, nameOne, nameTwo, firstCity, secondCity, placeEventCity, firstState, secondState, exceptionMessage,
            firstCountry, defaultCountry, placeEventTitle;

    @Before
    public void setUp() {
        defaultCountry = "United States";
        firstCountry = "America";
        summerTitle = "Family Reunion";
        fallTitle = "Karen's Graduation";
        placeEventTitle = "Somewhere Else";
        nameOne = "Washington Park";
        nameTwo = "The Chase Center";
        firstCity = "Chicago";
        secondCity = "Wilmington";
        placeEventCity = "Wilmington";
        firstState = "Illinois";
        secondState = "Delaware";
        exceptionMessage = "EventNotFound";

        addressOne = new Address(defaultCountry, firstCity, firstState);
        addressTwo = new Address(firstCountry, secondCity, secondState);
        addressThree = new Address(defaultCountry, placeEventCity, firstState);

        eventPlace = new Place(nameOne, addressThree);

        summerPlace = new Place(nameOne, addressOne);

        fallPlace = new Place(nameTwo, addressTwo);

        placeEvent = new FamilyEvent(placeEventTitle, new When(), eventPlace);

        summerStartDate = LocalDate.of(2020, Month.JULY, 21);
        summerEndDate = LocalDate.of(2020, Month.JULY, 24);
        summerStartTime = LocalTime.of(4, 0);
        summerWhen = new When(summerStartDate, summerEndDate, summerStartTime);

        summerEvent = new FamilyEvent(summerTitle, summerWhen, summerPlace);

        fallStartDate = LocalDate.of(2007, Month.MAY, 25);
        fallStartTime = LocalTime.of(7, 0);
        fallWhen = new When(fallStartDate, fallStartTime);

        fallEvent = new FamilyEvent(fallTitle, fallWhen, fallPlace);
    }

    @Test
    public void contextLoads() {
        assertNotNull(eventService);
    }

    @Test
    public void findByTitle() {
        Mockito.when(placeService.findByNameOfPlace(summerEvent.getPlace().getNameOfPlace()))
                .thenReturn(summerPlace);
        System.out.print(placeService);
        Mockito.when(familyEventRepository.findByTitle(summerTitle))
                .thenReturn(Optional.of(summerEvent));
        FamilyEvent foundEvent = eventService.findByTitle(summerEvent.getTitle());
        assertThat(foundEvent.getTitle(), is(equalTo(summerEvent.getTitle())));
    }

    @Test
    public void findByWhen() {
        Mockito.when(familyEventRepository.findByWhen(fallWhen))
                .thenReturn(Optional.of(fallEvent));
        FamilyEvent foundEvent = eventService.findByWhen(fallEvent.getWhen());
        assertThat(foundEvent.getWhen(), is(equalTo(fallEvent.getWhen())));
    }

    @Test
    public void findByCityTestOne() {
        Mockito.when(placeService.findAllPlacesByCity(Mockito.anyString()))
                .thenReturn(Arrays.asList(fallPlace, eventPlace));
        Mockito.when(familyEventRepository.findByPlace(Mockito.any(Place.class)))
                .thenReturn(Arrays.asList(fallEvent, placeEvent));
        List<FamilyEvent> foundList = eventService.findByCity(fallEvent.getPlace().getAddress().getCity());
        assertThat(foundList, hasSize(2));
    }

    @Test
    public void findByCityStateOne() {
        Mockito.when(placeService.findAllPlacesByCity(Mockito.anyString()))
                .thenReturn(Arrays.asList(fallPlace, eventPlace));
        Mockito.when(familyEventRepository.findByPlace(Mockito.any(Place.class)))
                .thenReturn(Arrays.asList(fallEvent, placeEvent));
        List<FamilyEvent> foundList = eventService.findByCity(fallEvent.getPlace().getAddress().getCity());
        assertThat(foundList, hasSize(2));
    }
}