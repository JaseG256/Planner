package com.msa.jrg.familyservice.resources;

import com.msa.jrg.core.model.ResourceNotFoundException;
import com.msa.jrg.familyservice.exception.FamilyEventNotFoundException;
import com.msa.jrg.familyservice.model.Address;
import com.msa.jrg.familyservice.model.FamilyEvent;
import com.msa.jrg.familyservice.model.Place;
import com.msa.jrg.familyservice.model.When;
import com.msa.jrg.familyservice.payload.FamilyEventRequest;
import com.msa.jrg.familyservice.payload.FamilyEventResponse;
import com.msa.jrg.familyservice.service.FamilyEventService;
import com.msa.jrg.familyservice.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class FamilyEventRestController {

    private final Logger logger = LoggerFactory.getLogger(FamilyEventRestController.class);

    @Qualifier("eventServicer")
    private final FamilyEventService eventService;

    @Qualifier("placeServicer")
    private final PlaceService placeService;


    public FamilyEventRestController(FamilyEventService eventService, PlaceService placeService) {
        this.eventService = eventService;
        this.placeService = placeService;
    }

    //    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @GetMapping(path = "/familyEvents")
    public List<FamilyEvent> findAll() {
        return eventService.listAll();
    }

    @GetMapping(path = "/familyEvent/{id}")
    @ResponseBody
    public FamilyEvent findOne(@PathVariable("id") Long id) {
        return eventService.getById(id).orElseThrow(() -> {
            String msg = String.format(
                    eventService.getPropertiesConfig().getFamily_event_exception_message() + " {}", id);
            logger.info(msg);
            return new FamilyEventNotFoundException(
                    eventService.getPropertiesConfig().getFamily_event_exception_message(),
                    eventService.getPropertiesConfig().getFamily_event_resource_name(),
                    eventService.getPropertiesConfig().getFamily_event_field_id(), id);
        });
    }

    //    @PreAuthorize("hasRole('ADMIN, USER')")
    @PostMapping("/familyEvent/make")
    public ResponseEntity<FamilyEventResponse> create(@RequestBody @Valid FamilyEventRequest eventRequest) {
        When createdWhen = getWhen(eventRequest);
        Address createdAddress = getAddress(eventRequest);
        Place createdPlace = getPlace(eventRequest, createdAddress);
        FamilyEvent createdEvent = saveFamilyEvent(eventRequest, createdWhen, createdPlace);

//        String msg = String.format(
//                eventService.getPropertiesConfig().getFamily_event_exception_message() + ",{}", createdEvent);
//        logger.info(msg);

        if (createdEvent == null)
            return ResponseEntity.noContent().build();

        URI location = getUri(createdEvent);

//        logger.info(String.format(
//                eventService.getPropertiesConfig().getFamily_event_resource_name() + ", {}", location));

        return ResponseEntity.created(location).body(new FamilyEventResponse(
                createdEvent.getTitle(), createdEvent.getWhen().getStartDate(),
                createdEvent.getWhen().getStartTime(), createdEvent.getPlace().getNameOfPlace())
        );
    }

    private FamilyEvent saveFamilyEvent(@RequestBody @Valid FamilyEventRequest eventRequest,
                                        When createdWhen, Place createdPlace) {
        return eventService.saveOrUpdate(
                new FamilyEvent(
                        eventRequest.getTitle(), createdWhen, createdPlace)
        );
    }

    private URI getUri(FamilyEvent createdEvent) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("familyEvent/{id}")
                .buildAndExpand(createdEvent.getId())
                .toUri();
    }

    private Place getPlace(@RequestBody @Valid FamilyEventRequest eventRequest, Address createdAddress) {
        return placeService.saveOrUpdate(new Place(eventRequest.getNameOfPlace(),
                eventRequest.getLocation(), createdAddress));
    }

    private Address getAddress(@RequestBody @Valid FamilyEventRequest eventRequest) {
        return new Address(eventRequest.getStreetNumber(), eventRequest.getCity(),
                eventRequest.getState(), eventRequest.getCountry(), eventRequest.getZipCode());
    }

    private When getWhen(@RequestBody @Valid FamilyEventRequest eventRequest) {
        return new When(eventRequest.getStartDate(), eventRequest.getEndDate(),
                eventRequest.getStartTime(), eventRequest.getEndTime());
    }

}
