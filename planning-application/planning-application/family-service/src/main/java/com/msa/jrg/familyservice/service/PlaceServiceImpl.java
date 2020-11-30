package com.msa.jrg.familyservice.service;

import com.msa.jrg.core.model.ResourceNotFoundException;
import com.msa.jrg.core.payload.ApiResponse;
import com.msa.jrg.familyservice.config.PlacePropertiesConfig;
import com.msa.jrg.familyservice.model.Address;
import com.msa.jrg.familyservice.model.Place;
import com.msa.jrg.familyservice.repository.PlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "placeServicer")
public class PlaceServiceImpl implements PlaceService {

    private final Logger logger = LoggerFactory.getLogger(PlaceServiceImpl.class);
    private final PlaceRepository placeRepository;
    private final PlacePropertiesConfig propertiesConfig;

    public PlaceServiceImpl(PlaceRepository placeRepository, PlacePropertiesConfig propertiesConfig) {
        this.placeRepository = placeRepository;
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    public List<Place> listAll() {
        List<Place> placeList = new ArrayList<>();
        placeRepository.findAll().forEach(placeList::add);
        return placeList;
    }

    @Override
    public Optional<Place> getById(Long id) {
        return placeRepository.findById(id);
    }

    @Override
    public Place saveOrUpdate(Place place) {
        return placeRepository.save(new Place(place.getNameOfPlace(), place.getLocation(),
                place.getAddress()));
    }

    @Override
    public ApiResponse delete(Long id) {
        return placeRepository.findById(id).map(place -> {
            placeRepository.delete(place);
            return new ApiResponse(true, propertiesConfig.getPlace_delete_apiResponse_message());
        }).orElseThrow(() -> getResourceNotFoundException(id));
    }

    @Override
    public Place findByNameOfPlace(String nameOfPlace) {
        return placeRepository.findByNameOfPlace(nameOfPlace).orElseThrow(() ->
                getResourceNotFoundException(nameOfPlace));
    }

    @Override
    public Place findByAddress(Address address) {
        return placeRepository.findByAddress(address)
                .orElseThrow(() -> getResourceNotFoundException(address));
    }

    @Override
    public List<Place> findAllPlaces(Address address) {
        return placeRepository.findAllPlaces(address);
    }

    @Override
    public List<Place> findAllPlacesByCity(String city) {
        Address address = new Address(null, city, null);
        return placeRepository.findAllPlaces(address);
    }

    @Override
    public List<Place> findAllPlacesByState(String state) {
        Address address = new Address(null, null, state);
        return placeRepository.findAllPlaces(address);
    }

    @Override
    public PlaceRepository getRepository() {
        return placeRepository;
    }

    private ResourceNotFoundException getResourceNotFoundException(Object nameOfPlace) {
        logException(nameOfPlace);
        return new ResourceNotFoundException(
                propertiesConfig.getPlace_exception_message(),
                propertiesConfig.getPlace_resource_name(),
                propertiesConfig.getPlace_field_id(), nameOfPlace);
    }

    private void logException(Object fieldValue) {
        String msg = String.format(propertiesConfig.getPlace_exception_message() + " {}", fieldValue);
        logger.info(msg);
    }
}
