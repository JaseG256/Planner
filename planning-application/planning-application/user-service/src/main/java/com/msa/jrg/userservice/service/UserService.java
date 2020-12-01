package com.msa.jrg.userservice.service;

import com.msa.jrg.core.service.CRUDService;
import com.msa.jrg.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends CRUDService<User> {

    List<User> listAll();

    User findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}