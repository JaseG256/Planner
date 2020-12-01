package com.msa.jrg.userservice.loader;

import com.msa.jrg.userservice.model.Role;
import com.msa.jrg.userservice.model.RoleName;
import com.msa.jrg.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleLoader implements ApplicationRunner {

    private final RoleService roleService;

    @Autowired
    public RoleLoader(@Qualifier("roleServicer") RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(ApplicationArguments args) {
        Role role = new Role(RoleName.ROLE_USER);
        roleService.saveOrUpdate(role);

        role = new Role(RoleName.ROLE_ADMIN);
        roleService.saveOrUpdate(role);
    }
}
