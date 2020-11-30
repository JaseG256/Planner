package com.msa.jrg.familyservice.service;

import com.msa.jrg.core.model.ResourceNotFoundException;
import com.msa.jrg.core.payload.ApiResponse;
import com.msa.jrg.familyservice.config.FamilyEventPropertiesConfig;
import com.msa.jrg.familyservice.model.FamilyEvent;
import com.msa.jrg.familyservice.model.Place;
import com.msa.jrg.familyservice.model.When;
import com.msa.jrg.familyservice.repository.FamilyEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "eventServicer")
public class FamilyEventServiceImpl implements FamilyEventService {

    private final Logger logger = LoggerFactory.getLogger(FamilyEventServiceImpl.class);
    private final FamilyEventRepository eventRepository;
    private final FamilyEventPropertiesConfig propertiesConfig;
    private final PlaceService placeService;

    public FamilyEventServiceImpl(FamilyEventRepository eventRepository, FamilyEventPropertiesConfig propertiesConfig, PlaceService placeService) {
        this.eventRepository = eventRepository;
        this.propertiesConfig = propertiesConfig;
        this.placeService = placeService;
    }

    @Override
    public List<FamilyEvent> listAll() {
        List<FamilyEvent> eventList = new ArrayList<>();
        eventRepository.findAll().forEach(eventList::add);
        return eventList;
    }

    @Override
    public Optional<FamilyEvent> getById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public FamilyEvent saveOrUpdate(FamilyEvent familyEvent) {
        return eventRepository.save(new FamilyEvent(familyEvent.getTitle(), familyEvent.getWhen(),
                familyEvent.getPlace()));
    }

    @Override
    public ApiResponse delete(Long id) {
        return eventRepository.findById(id).map(familyEvent -> {
            logException(id);
            eventRepository.delete(familyEvent);
            return new ApiResponse(true, propertiesConfig.getFamily_event_delete_apiResponse_message());
        }).orElseThrow(() -> getResourceNotFoundException(id));
    }

    @Override
    public FamilyEvent findByTitle(String title) {
        return eventRepository.findByTitle(title).orElseThrow(
                () -> getResourceNotFoundException(title));
    }

    @Override
    public FamilyEvent findByWhen(When when) {
        return eventRepository.findByWhen(when).orElseThrow(
                () -> getResourceNotFoundException(when));
    }

    @Override
    public List<FamilyEvent> findByPlace(Place place) {
        return eventRepository.findByPlace(place);
    }

    @Override
    public List<FamilyEvent> findByState(String state) {
        List<FamilyEvent> resultList = new ArrayList<>();
        placeService.findAllPlacesByState(state)
                .forEach(place -> {
                    List<FamilyEvent> familyEvent = eventRepository.findByPlace(place);
                    familyEvent.forEach(familyEvent1 -> {
                        if (!resultList.contains(familyEvent1))
                            resultList.add(familyEvent1);
                    });
                });
        return resultList;
    }

    @Override
    public List<FamilyEvent> findByCity(String city) {
        List<FamilyEvent> resultList = new ArrayList<>();
        placeService.findAllPlacesByCity(city)
                .forEach(place -> {
                    List<FamilyEvent> familyEvent = eventRepository.findByPlace(place);
                    familyEvent.forEach(familyEvent1 -> {
                        if (!resultList.contains(familyEvent1))
                            resultList.add(familyEvent1);
                    });
                });
        return resultList;
    }

    private ResourceNotFoundException getResourceNotFoundException(Object fieldValue) {
        logException(fieldValue);
        return new ResourceNotFoundException(
                propertiesConfig.getFamily_event_exception_message(),
                propertiesConfig.getFamily_event_resource_name(),
                propertiesConfig.getFamily_event_field_id(), fieldValue
        );
    }

    private void logException(Object fieldValue) {
        String msg = String.format(propertiesConfig.getFamily_event_exception_message(), fieldValue);
        logger.info(msg);
    }

    public FamilyEventPropertiesConfig getPropertiesConfig() {
        return propertiesConfig;
    }
}

