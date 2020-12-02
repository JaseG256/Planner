package com.msa.jrg.userservice.repository;

import com.msa.jrg.userservice.model.Role;
import com.msa.jrg.userservice.model.RoleName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void findByRolenameTest1() {
        // given
        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);
        entityManager.persist(userRole);
        entityManager.flush();
        // when
        Optional<Role> found = roleRepository.findByName(userRole.getName());
        Role foundRole = found.get();
        // then
        assertEquals(foundRole.getName(), userRole.getName());

    }

    @Test
    public void findByRolenameTest2() {
        // given
        Role adminRole = new Role();
        adminRole.setName(RoleName.ROLE_ADMIN);
        entityManager.persist(adminRole);
        entityManager.flush();
        // when
        Optional<Role> found = roleRepository.findByName(adminRole.getName());
        Role foundRole = found.get();
        // then
        assertEquals(foundRole.getName(), adminRole.getName());

    }

}