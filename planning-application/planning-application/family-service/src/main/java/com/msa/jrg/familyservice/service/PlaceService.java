package com.msa.jrg.familyservice.service;

import com.msa.jrg.core.payload.ApiResponse;
import com.msa.jrg.core.service.CRUDService;
import com.msa.jrg.familyservice.model.Address;
import com.msa.jrg.familyservice.model.Place;
import com.msa.jrg.familyservice.repository.PlaceRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceService extends CRUDService<Place> {

    List<Place> listAll();

    List<Place> findAllPlacesByState(String state);

    List<Place> findAllPlaces(Address address);

    Optional<Place> getById(Long id);

    Place findByNameOfPlace(String nameOfPlace);

    Place findByAddress(Address address);

    PlaceRepository getRepository();

    List<Place> findAllPlacesByCity(String city);
}

