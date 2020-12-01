package com.msa.jrg.userservice.service;

import com.msa.jrg.core.service.CRUDService;
import com.msa.jrg.userservice.model.Role;
import com.msa.jrg.userservice.model.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleService extends CRUDService<Role> {

    Optional<Role> findByName(RoleName roleName);

    List<Role> listAll();
}

