package com.msa.jrg.familyservice.repository;

import com.msa.jrg.core.model.AppException;
import com.msa.jrg.familyservice.model.Address;
import com.msa.jrg.familyservice.model.FamilyEvent;
import com.msa.jrg.familyservice.model.Place;
import com.msa.jrg.familyservice.model.When;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FamilyEventRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    FamilyEventRepository eventRepository;

    FamilyEvent summerEvent, fallEvent, placeEvent;
    When summerWhen, fallWhen;
    LocalDate summerStartDate, summerEndDate, fallStartDate, fallEndDate;
    LocalTime summerStartTime, fallStartTime, fallEndTime;
    Place summerPlace, fallPlace, eventPlace;
    Address addressOne, addressTwo;
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

        eventPlace = new Place(nameOne, placeEventCity);
        entityManager.persist(eventPlace);
        entityManager.flush();

        summerPlace = new Place(nameOne, addressOne);
        entityManager.persist(summerPlace);
        entityManager.flush();

        fallPlace = new Place(nameTwo, addressTwo);
        entityManager.persist(fallPlace);
        entityManager.flush();

        placeEvent = new FamilyEvent(placeEventTitle, new When(), eventPlace);
        entityManager.persist(placeEvent);
        entityManager.flush();

        summerStartDate = LocalDate.of(2020, Month.JULY, 21);
        summerEndDate = LocalDate.of(2020, Month.JULY, 24);
        summerStartTime = LocalTime.of(4, 0);
        summerWhen = new When(summerStartDate, summerEndDate, summerStartTime);

        summerEvent = new FamilyEvent(summerTitle, summerWhen, summerPlace);
        entityManager.persist(summerEvent);
        entityManager.flush();

        fallStartDate = LocalDate.of(2007, Month.MAY, 25);
        fallStartTime = LocalTime.of(7, 0);
        fallWhen = new When(fallStartDate, fallStartTime);

        fallEvent = new FamilyEvent(fallTitle, fallWhen, fallPlace);
        entityManager.persist(fallEvent);
        entityManager.flush();
    }

    @Test
    public void contextLoads() {
        assertNotNull(eventRepository);
    }

    @Test
    public void findByTitleWorks() {
        FamilyEvent foundEvent = eventRepository.findByTitle(summerEvent.getTitle())
                .orElseThrow(() -> new AppException(exceptionMessage));
        assertThat(foundEvent.getTitle(), is(equalTo(summerEvent.getTitle())));
        assertThat(foundEvent.getTitle(), is(not(equalTo(fallEvent.getTitle()))));
    }

    @Test
    public void findByWhen() {
        FamilyEvent foundEvent = eventRepository.findByWhen(fallEvent.getWhen())
                .orElseThrow(() -> new AppException(exceptionMessage));
        assertThat(foundEvent.getTitle(), is(equalTo(fallEvent.getTitle())));
        assertThat(foundEvent.getWhen(), is(not(equalTo(summerEvent.getWhen()))));
    }
}