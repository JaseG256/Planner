package com.msa.jrg.familyservice.service;

import com.msa.jrg.familyservice.config.ServiceUnitTestConfig;
import com.msa.jrg.familyservice.model.Address;
import com.msa.jrg.familyservice.model.Place;
import com.msa.jrg.familyservice.repository.PlaceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceUnitTestConfig.class)
public class PlaceServiceImplTest {

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceRepository placeRepositoryMock;

    String nameOfPlace, anotherName, thirdNameOfPlace, aleteredPlaceName, exceptionMessage, firstName, firstStreetNumber, secondStreetNumber,
            firstCity, secondCity, thirdCity, firstState, secondState, thirdState, firstCountry, defaultCountry;
    int rightZipCode, wrongZipCode;;
    Long firstId, secondId, thirdId;
    Address rightAddress, wrongAddress, thirdAddress, alteredAddress;
    Place rightPlace, wrongPlace, thirdPlace, alteredPlace;

    @Before
    public void setUp() {
        defaultCountry = "United States";
        firstCountry = "America";
        nameOfPlace = "Six Flags";
        anotherName = "Disney World";
        thirdNameOfPlace = "Somewhere Else";
        aleteredPlaceName = "Altered Place";
        exceptionMessage = "Place not found";
        firstName = "Black Expo";
        firstStreetNumber = "2098";
        secondStreetNumber = "3702";
        firstCity = "Indianapolis";
        secondCity = "Northern";
        thirdCity = "Northern";
        firstState = "Indiana";
        secondState = "New Jersey";
        thirdState = "Indiana";
        rightZipCode = 37883;
        wrongZipCode = 19702;

        firstId = 1L;
        secondId = 2L;
        thirdId = 3L;


        rightAddress = new Address(firstStreetNumber, firstCity, firstState, firstCountry, rightZipCode);
        wrongAddress = new Address(secondStreetNumber, defaultCountry, secondCity, secondState);
        thirdAddress = new Address(defaultCountry, thirdCity, thirdState);
        alteredAddress = new Address(secondStreetNumber, defaultCountry, secondCity, secondState);

        rightPlace = new Place(nameOfPlace, rightAddress);
        rightPlace.setId(firstId);
        wrongPlace = new Place(anotherName, wrongAddress);
        wrongPlace.setId(secondId);
        thirdPlace = new Place(thirdNameOfPlace, thirdAddress);
        alteredPlace = new Place(aleteredPlaceName, alteredAddress);
    }

    @Test
    public void contextLoads() {
        assertNotNull(placeService);
        assertNotNull(placeService.getRepository());
    }

    @Test
    public void findByNameOfPlace() {
        Mockito.when(placeRepositoryMock.findByNameOfPlace(Mockito.anyString()))
                .thenReturn(Optional.of(rightPlace));
        Place foundPlace = placeService.findByNameOfPlace(nameOfPlace);
        assertThat(foundPlace.getNameOfPlace(), is(equalTo(nameOfPlace)));
        assertThat(foundPlace.getNameOfPlace(), is(not(equalTo(anotherName))));
    }

    @Test
    public void findByAddress() {
        Mockito.when(placeRepositoryMock.findByAddress(Mockito.any(Address.class)))
                .thenReturn(Optional.of(rightPlace));
        Place foundPlace = placeService.findByAddress(rightAddress);
        assertThat(foundPlace.getAddress(), is(equalTo(rightPlace.getAddress())));
        assertThat(foundPlace.getNameOfPlace(), is(equalTo(rightPlace.getNameOfPlace())));
        assertThat(foundPlace.getNameOfPlace(), is(not(equalTo(wrongPlace.getNameOfPlace()))));
    }

    @Test
    public void findAllPlacesTest() {
        Mockito.when(placeRepositoryMock.findAllPlaces(Mockito.any(Address.class)))
                .thenReturn(Arrays.asList(wrongPlace, alteredPlace));
        List<Place> foundPlaceList = placeService.findAllPlaces(wrongPlace.getAddress());
        assertThat(foundPlaceList.get(0).getAddress(), is(equalTo(foundPlaceList.get(1).getAddress())));
        assertThat(foundPlaceList, hasSize(2));
    }

    @Test
    public void findAllPlacesTestTwo() {
        Mockito.when(placeRepositoryMock.findAllPlaces(Mockito.any(Address.class)))
                .thenReturn(Arrays.asList(wrongPlace, thirdPlace, alteredPlace));
        List<Place> foundPlaceList = placeService.findAllPlaces(new Address(
                null, wrongPlace.getAddress().getCity(), null));
        assertThat(foundPlaceList.get(0).getAddress(), is(not(equalTo(foundPlaceList.get(1).getAddress()))));
        assertThat(foundPlaceList.get(0).getAddress(), is(equalTo(foundPlaceList.get(2).getAddress())));
        assertThat(foundPlaceList.get(0).getAddress().getCity(),
                is(equalTo(foundPlaceList.get(1).getAddress().getCity())));
        assertThat(foundPlaceList.get(0).getAddress().getState(),
                is(not(equalTo(foundPlaceList.get(1).getAddress().getState()))));
        assertThat(foundPlaceList, hasSize(3));
    }

    @Test
    public void findAllByCityTest() {
        Mockito.when(placeRepositoryMock.findAllPlaces(Mockito.any(Address.class)))
                .thenReturn(Arrays.asList(wrongPlace, thirdPlace));
        List<Place> foundList = placeService.findAllPlacesByCity(wrongPlace.getAddress().getCity());
        assertThat(foundList.get(0).getAddress().getCity(), is(equalTo(thirdPlace.getAddress().getCity())));
        assertThat(foundList.get(0).getAddress().getCity(), is(not(equalTo(thirdPlace.getAddress().getState()))));
        assertThat(foundList.get(1).getAddress().getCity(), is(equalTo(thirdPlace.getAddress().getCity())));
        assertThat(foundList, hasSize(2));
    }

    @Test
    public void findAllByStateTest() {
        Mockito.when(placeRepositoryMock.findAllPlaces(Mockito.any(Address.class)))
                .thenReturn(Arrays.asList(rightPlace, thirdPlace));
        List<Place> foundList = placeService.findAllPlacesByCity(rightPlace.getAddress().getState());
        assertThat(foundList.get(0).getAddress().getState(), is(equalTo(thirdPlace.getAddress().getState())));
        assertThat(foundList.get(0).getAddress().getState(), is(not(equalTo(thirdPlace.getAddress().getCity()))));
        assertThat(foundList.get(1).getAddress().getState(), is(equalTo(thirdPlace.getAddress().getState())));
        assertThat(foundList, hasSize(2));
    }}