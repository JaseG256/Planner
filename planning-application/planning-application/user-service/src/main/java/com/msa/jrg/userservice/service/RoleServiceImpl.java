package com.msa.jrg.userservice.service;

import com.msa.jrg.core.payload.ApiResponse;
import com.msa.jrg.userservice.config.RolePropertiesConfig;
import com.msa.jrg.userservice.exception.RoleNotFoundException;
import com.msa.jrg.userservice.model.Role;
import com.msa.jrg.userservice.model.RoleName;
import com.msa.jrg.userservice.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "roleServicer")
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final RolePropertiesConfig propertiesConfig;

    public RoleServiceImpl(RoleRepository roleRepository, RolePropertiesConfig propertiesConfig) {
        this.roleRepository = roleRepository;
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    public List<Role> listAll() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    @Override
    public Optional<Role> getById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role saveOrUpdate(Role domainObject) {
        return roleRepository.save(domainObject);
    }

    @Override
    public ApiResponse delete(Long id) {
        return roleRepository.findById(id).map(role -> {
            roleRepository.delete(role);
            return new ApiResponse(true, propertiesConfig.getRole_delete_apiResponse_message());
        }).orElseThrow(() -> new RoleNotFoundException(
                propertiesConfig.getRole_exception_message(),
                propertiesConfig.getRole_resource_name(),
                propertiesConfig.getRole_field_id(), id));
    }

    @Override
    public Optional<Role> findByName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }
}
