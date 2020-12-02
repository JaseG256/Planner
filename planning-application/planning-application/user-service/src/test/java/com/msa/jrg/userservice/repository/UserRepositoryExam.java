package com.msa.jrg.userservice.repository;

import com.msa.jrg.userservice.exception.UserNotFoundException;
import com.msa.jrg.userservice.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryExam {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    User jason, jamie, eric;



    @Before
    public void setUp() {
        jason = new User("jason", "jason@yahoo.com", "jumpshot");
        // given
        entityManager.persist(jason);
        entityManager.flush();

        jamie = new User("jamie", "jamie@gmail.com", "runner");
        entityManager.persist(jamie);
        entityManager.flush();

        eric = new User("eric", "eric@yahoo.com", "rebounder");
    }

    @Test
    public void findByUsernameTest() {
        // when
        User found = userRepository.findByUsername(jason.getUsername())
                .orElseThrow(() -> new UserNotFoundException("", "", "", ""));
        // then
        assertEquals(found.getUsername(), jason.getUsername());

    }

    @Test
    public void findByEmailTest() {
        // when
        Optional<User> found = userRepository.findByEmail(jason.getEmail());
        User foundUser = found.orElseThrow(UserNotFoundException::new);
        // then
        assertEquals(foundUser.getEmail(), jason.getEmail());
    }

    @Test
    public void findByUsernameOrEmailTestUsername() {
        // when
        Optional<User> found = userRepository.findByUsernameOrEmail(jason.getUsername(), null);
        User foundUser = found.orElseThrow(UserNotFoundException::new);
        // then
        assertEquals(foundUser.getUsername(), jason.getUsername());
    }

    @Test
    public void findByUsernameOrEmailTestEmail() {
        // when
        Optional<User> found = userRepository.findByUsernameOrEmail(null, jason.getEmail());
        User foundUser = found.orElseThrow(UserNotFoundException::new);
        // then
        assertEquals(foundUser.getEmail(), jason.getEmail());
    }

    @Test
    public void findByIdIn() {
        List<User> userList = userRepository.findAll();
        List<Long> userIds = new ArrayList<>();
        userList.forEach((user -> userIds.add(user.getId())));
        List<User> userListFromIds = userRepository.findByIdIn(userIds);

        assertEquals(userList, userListFromIds);
    }


    @Test
    public void existsByUsernameTestTrue() {
        assertEquals(userRepository.existsByUsername(jamie.getUsername()),
                userRepository.existsByUsername(jason.getUsername()));
    }

    @Test
    public void existsByUsernameTestFalse() {
        assertNotEquals(userRepository.existsByUsername(jason.getUsername()),
                userRepository.existsByUsername(eric.getUsername()));
    }

    @Test
    public void existsByEmailTestTrue() {
        assertEquals(userRepository.existsByEmail(jamie.getEmail()),
                userRepository.existsByEmail(jason.getEmail()));
    }

    @Test
    public void existsByEmailFalse() {
        assertNotEquals(userRepository.existsByEmail(eric.getEmail()),
                userRepository.existsByEmail(jason.getEmail()));
    }
}