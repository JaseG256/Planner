package com.msa.jrg.familyservice.resources;

import com.msa.jrg.core.util.JsonUtil;
import com.msa.jrg.familyservice.config.ControllerUnitTestConfig;
import com.msa.jrg.familyservice.config.FamilyEventPropertiesConfig;
import com.msa.jrg.familyservice.model.Address;
import com.msa.jrg.familyservice.model.FamilyEvent;
import com.msa.jrg.familyservice.model.Place;
import com.msa.jrg.familyservice.model.When;
import com.msa.jrg.familyservice.payload.FamilyEventRequest;
import com.msa.jrg.familyservice.payload.FamilyEventResponse;
import com.msa.jrg.familyservice.service.FamilyEventService;
import com.msa.jrg.familyservice.service.PlaceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("controller_test")
@RunWith(SpringRunner.class)
@WebMvcTest(FamilyEventRestController.class)
@ContextConfiguration(classes = {
        ControllerUnitTestConfig.class,
})
public class FamilyEventRestControllerTest extends JsonUtil {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FamilyEventService familyEventService;

    @MockBean
    PlaceService placeService;

    @Autowired
    FamilyEventPropertiesConfig propertiesConfig;

    FamilyEvent summerEvent, fallEvent;
    List<FamilyEvent> familyEventList;
    When summerWhen, fallWhen;
    LocalDate summerStartDate, summerEndDate, fallStartDate, fallEndDate;
    LocalTime summerStartTime, fallStartTime, fallEndTime;
    Place summerPlace, fallPlace;
    Address addressOne, addressTwo;
    String summerTitle, fallTitle, nameOne, nameTwo, firstCity, secondCity, firstState, secondState, exceptionMessage;
    Long summerId, fallId, wrongId;
    String defaultCountry;
    String firstCountry;

    @Before
    public void setUp() {
        defaultCountry = "United States";
        firstCountry = "America";
        summerTitle = "Family Reunion";
        fallTitle = "Karen's Graduation";
        nameOne = "Washington Park";
        nameTwo = "The Chase Center";
        firstCity = "Chicago";
        secondCity = "Wilmington";
        firstState = "Illinois";
        secondState = "Delaware";
        exceptionMessage = "EventNotFound";
        summerId = 1L;
        fallId = 2L;
        wrongId = 3L;

        addressOne = new Address(defaultCountry, firstCity, firstState);
        addressTwo = new Address(firstCountry, secondCity, secondState);

        summerPlace = new Place(nameOne, addressOne);

        fallPlace = new Place(nameTwo, addressTwo);

        summerStartDate = LocalDate.of(2020, Month.JULY, 21);
        summerEndDate = LocalDate.of(2020, Month.JULY, 24);
        summerStartTime = LocalTime.of(4, 0);
        summerWhen = new When(summerStartDate, summerEndDate, summerStartTime);

        summerEvent = new FamilyEvent(summerTitle, summerWhen, summerPlace);
        summerEvent.setId(summerId);

        fallStartDate = LocalDate.of(2007, Month.MAY, 25);
        fallStartTime = LocalTime.of(7, 0);
        fallWhen = new When(fallStartDate, fallStartTime);

        fallEvent = new FamilyEvent(fallTitle, fallWhen, fallPlace);
        fallEvent.setId(fallId);

        familyEventList = Arrays.asList(summerEvent, fallEvent);
    }

    @Test
    public void contextLoads() {
        assertNotNull(familyEventService);
    }

    @Test
    public void findAll() throws Exception {
        Mockito.when(familyEventService.listAll())
                .thenReturn(familyEventList);
        mockMvc.perform(get("/api/event/familyEvents")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(equalTo(summerEvent.getTitle()))))
                .andExpect(jsonPath("$[1].title", is(equalTo(fallEvent.getTitle()))));
    }

    @Test
    public void findOne() throws Exception {
        Mockito.when(familyEventService.getById(Mockito.anyLong()))
                .thenReturn(Optional.of(fallEvent));
        mockMvc.perform(get("/api/event/familyEvent/" + fallEvent.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String string = result.getResponse().getContentAsString();
                    assertTrue(string.contains("Karen's"));
                    assertFalse(string.contains("Family"));
                });
    }

    @Test
    public void create() throws Exception {
        Mockito.when(placeService.saveOrUpdate(Mockito.any(Place.class)))
                .thenReturn(fallPlace);
        Mockito.when(familyEventService.saveOrUpdate(Mockito.any(FamilyEvent.class)))
                .thenReturn(fallEvent);
        FamilyEventRequest request = new FamilyEventRequest(fallEvent.getTitle(), fallEvent.getWhen().getStartDate(),
                fallEvent.getWhen().getEndDate(), fallEvent.getWhen().getStartTime(), fallEvent.getWhen().getEndTime(),
                fallEvent.getPlace().getNameOfPlace(), fallEvent.getPlace().getLocation(),
                fallEvent.getPlace().getAddress().getStreetNumber(), fallEvent.getPlace().getAddress().getCity(),
                fallEvent.getPlace().getAddress().getState(), fallEvent.getPlace().getAddress().getCountry(),
                fallEvent.getPlace().getAddress().getZipCode());
        mockMvc.perform(post("/api/event/familyEvent/make")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    String string = result.getResponse().getContentAsString();
                    FamilyEventResponse response = JsonUtil.fromJson(string, FamilyEventResponse.class);
                    assertThat(response.getTitle(), is(equalTo(fallEvent.getTitle())));
                    assertThat(response.getClass(), is(equalTo(FamilyEventResponse.class)));
                });
    }

//    @Test
//    public void createInvalidInputThrowsException() throws Exception {
//        Mockito.when(placeService.saveOrUpdate(Mockito.any(Place.class)))
//                .thenReturn(fallPlace);
//        Mockito.when(familyEventService.saveOrUpdate(Mockito.any(FamilyEvent.class)))
//                .thenReturn(fallEvent);
//        FamilyEventRequest request = new FamilyEventRequest(fallEvent.getTitle(), fallEvent.getWhen().getStartDate(),
//                fallEvent.getWhen().getEndDate(), fallEvent.getWhen().getStartTime(), fallEvent.getWhen().getEndTime(),
//                fallEvent.getPlace().getNameOfPlace(), fallEvent.getPlace().getLocation(),
//                fallEvent.getPlace().getAddress().getStreetNumber(), fallEvent.getPlace().getAddress().getCity(),
//                fallEvent.getPlace().getAddress().getState(), fallEvent.getPlace().getAddress().getCountry(),
//                fallEvent.getPlace().getAddress().getZipCode());
//        mockMvc.perform(post("/api/event/familyEvent/make")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.toJson(request)))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(result -> {
//                    String string = result.getResponse().getContentAsString();
//                    FamilyEventResponse response = JsonUtil.fromJson(string, FamilyEventResponse.class);
//                    assertThat(response.getTitle(), is(equalTo(fallEvent.getTitle())));
//                    assertThat(response.getClass(), is(equalTo(FamilyEventResponse.class)));
//                });
//    }

}