package com.msa.jrg.familyservice.repository;

import com.msa.jrg.core.model.AppException;
import com.msa.jrg.familyservice.config.PlacePropertiesConfig;
import com.msa.jrg.familyservice.model.Address;
import com.msa.jrg.familyservice.model.Place;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlaceRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PlacePropertiesConfig propertiesConfig;


    Place rightPlace, wrongPlace;
    List<Place> sortedPlaceList, singlePlaceList;
    Sort sort;
    Address addressOne, addressTwo;
    String firstName, secondName, firstStreetNumber, secondStreetNumber, firstCity, secondCity, firstState,
            secondState, firstCountry, defaultCountry, exceptionMessage, citySortString;
    int rightZipCode, wrongZipCode;

    @Before
    public void setUp() {
        defaultCountry = "United States";
        firstName = "Black Expo";
        secondName = "Six Flags";
        firstStreetNumber = "2098";
        firstCity = "Indianapolis";
        secondCity = "Northern";
        firstState = "Indiana";
        secondState = "New Jersey";
        exceptionMessage = "Place not Found";
        citySortString = "CITY(name)";
        rightZipCode = 37883;
        wrongZipCode = 19702;

        sort = JpaSort.unsafe(citySortString);

        addressOne = new Address(firstStreetNumber, firstCity, firstState, defaultCountry, rightZipCode);
        addressTwo = new Address(defaultCountry, secondCity, secondState);

        rightPlace = new Place(firstName, addressOne);
        entityManager.persist(rightPlace);
        entityManager.flush();

        wrongPlace = new Place(secondName, addressTwo);
        entityManager.persist(wrongPlace);
        entityManager.flush();

        sortedPlaceList = Arrays.asList(rightPlace, wrongPlace);
        singlePlaceList = Arrays.asList(wrongPlace, rightPlace);
    }

    @Test
    public void contextLoads() {
        assertNotNull(placeRepository);
    }

    @Test
    public void findByNameOfPlaceWorks() {
        String nameOfPlace = "Navy Pier";
        String wrongName = "Disney Land";
        Place place = new Place(nameOfPlace);
        entityManager.persist(place);
        entityManager.flush();
        Optional<Place> found = placeRepository.findByNameOfPlace(place.getNameOfPlace());
        Place foundPlace = found.orElseThrow(() -> new AppException(exceptionMessage));
        assertThat(foundPlace.getNameOfPlace(), is(equalTo(place.getNameOfPlace())));
        assertThat(foundPlace.getNameOfPlace(), is(equalTo(nameOfPlace)));
        assertThat(foundPlace.getNameOfPlace(), is(not(equalTo(wrongName))));
    }

//    @Test
//    public void findByAddress_CityWorks() {
//        Optional<Place> found = placeRepository.findByAddress_City(secondCity);
//        Place foundPlace = found.orElseThrow(() -> new AppException(exceptionMessage));
//        assertThat(foundPlace.getAddress().getCity(), is(equalTo(wrongPlace.getAddress().getCity())));
//    }

    @Test
    public void findByAddressWorks() {
        Place foundPlace =
                placeRepository.findByAddress(rightPlace.getAddress())
                        .orElseThrow(() -> new AppException(exceptionMessage));
        assertThat(foundPlace.getAddress(), is(equalTo(rightPlace.getAddress())));
        assertThat(foundPlace.getNameOfPlace(), is(equalTo(rightPlace.getNameOfPlace())));
        assertThat(foundPlace.getAddress(), is(not(equalTo(wrongPlace.getAddress()))));
    }

    @Test
    public void findAllPlacesTest() {
        List<Place> actualPlaceList = placeRepository.findAllPlaces(new Address(defaultCountry, secondCity, secondState));
        assertThat(actualPlaceList.get(0).getNameOfPlace(),
                is(equalTo(sortedPlaceList.get(1).getNameOfPlace())));
    }

}