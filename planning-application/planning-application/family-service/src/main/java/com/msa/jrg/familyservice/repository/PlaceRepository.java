package com.msa.jrg.familyservice.repository;

import com.msa.jrg.familyservice.model.Address;
import com.msa.jrg.familyservice.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query(value = "SELECT p FROM Place p WHERE p.address = ?1")
    List<Place> findAllPlaces(Address address);

    Optional<Place> findByNameOfPlace(String nameOfPlace);

    Optional<Place> findByAddress(Address address);

//    Optional<Place> findByAddress_City(String city);
//
//    Optional<Place> findByAddress_State(String state);
//
//    Optional<Place> findByAddress_Country(String country);

}
