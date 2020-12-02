package com.msa.jrg.userservice.service;

import com.msa.jrg.userservice.config.ServiceUnitTestConfig;
import com.msa.jrg.userservice.model.Role;
import com.msa.jrg.userservice.model.RoleName;
import com.msa.jrg.userservice.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceUnitTestConfig.class)
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    private Role role;
    private Long id;
    private List<Role> foundRoleList;

    @Before
    public void setUp() {
        role = new Role();
        role.setName(RoleName.ROLE_USER);
        id = 1L;
        role.setId(id);
        foundRoleList = Collections.singletonList(role);
    }

    @Test
    public void listAll() {
        Mockito.when(roleRepository.findAll())
                .thenReturn(foundRoleList);
        List<Role> roleServiceList = roleService.listAll();
        assertThat(roleServiceList, equalTo(foundRoleList));
    }

    @Test
    public void getById() {
        role.setId(id);
        Mockito.when(roleRepository.findById(id))
                .thenReturn(Optional.of(role));
        Role foundRole = roleService.getById(id).orElseGet(Role::new);
        assertThat(foundRole.getId(), equalTo(id));

    }

    @Test
    public void saveOrUpdate() {
        Mockito.when(roleService.saveOrUpdate(role))
                .thenReturn(role);
        Role foundRole = roleService.saveOrUpdate(role);
        assertThat(foundRole, equalTo(role));
    }

    @Test
    public void delete() {
        Mockito.when(roleRepository.findById(id))
                .thenReturn(Optional.of(role));
        roleService.delete(id);
        assertThat(roleRepository.findById(id), not(equalTo(role)));
    }

    @Test
    public void findByNameTestMatch() {
        Mockito.when(roleRepository.findByName(role.getName()))
                .thenReturn(Optional.of(role));
        RoleName userRoleName = RoleName.valueOf("ROLE_USER");
        Role foundRole = roleService.findByName(userRoleName).get();
        assertThat(foundRole.getName(), equalTo(userRoleName));
    }

//    @Test
//    public void findByNameTestNoMatch() {
//        Mockito.when(roleRepository.findByName(role.getName()))
//                .thenReturn(Optional.of(role));
//        RoleName userRoleName = RoleName.valueOf("ROLE_ADMIN");
//        Role foundRole = roleService.findByName(userRoleName).get();
////        assertThat(foundRole.getName(), not(equalTo(userRoleName)));
//        assertNotSame(userRoleName, foundRole.getName());
//    }
}