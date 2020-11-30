package com.msa.jrg.familyservice.repository;

import com.msa.jrg.familyservice.model.FamilyEvent;
import com.msa.jrg.familyservice.model.Place;
import com.msa.jrg.familyservice.model.When;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FamilyEventRepository extends JpaRepository<FamilyEvent, Long> {

    Optional<FamilyEvent> findByWhen(When when);

    Optional<FamilyEvent> findByTitle(String title);

    List<FamilyEvent> findByPlace(Place place);
}
