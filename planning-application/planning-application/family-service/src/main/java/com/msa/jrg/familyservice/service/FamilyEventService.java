package com.msa.jrg.familyservice.service;

import com.msa.jrg.core.payload.ApiResponse;
import com.msa.jrg.core.service.CRUDService;
import com.msa.jrg.familyservice.config.FamilyEventPropertiesConfig;
import com.msa.jrg.familyservice.model.FamilyEvent;
import com.msa.jrg.familyservice.model.Place;
import com.msa.jrg.familyservice.model.When;

import java.util.List;
import java.util.Optional;

public interface FamilyEventService extends CRUDService<FamilyEvent> {

    List<FamilyEvent> listAll();

    List<FamilyEvent> findByPlace(Place place);

    List<FamilyEvent> findByState(String state);

    List<FamilyEvent> findByCity(String city);

    Optional<FamilyEvent> getById(Long id);

    FamilyEvent saveOrUpdate(FamilyEvent event);

    ApiResponse delete(Long id);

    FamilyEvent findByTitle(String title);

    FamilyEvent findByWhen(When when);

    FamilyEventPropertiesConfig getPropertiesConfig();
}
