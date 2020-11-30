package com.msa.jrg.core.service;

import com.msa.jrg.core.payload.ApiResponse;

import java.util.List;
import java.util.Optional;

public interface CRUDService<T> {

    List<?> listAll();

    Optional<T> getById(Long id);

    T saveOrUpdate(T domainObject);

    ApiResponse delete(Long id);
}
