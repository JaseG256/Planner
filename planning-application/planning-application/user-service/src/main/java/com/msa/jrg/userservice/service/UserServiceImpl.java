package com.msa.jrg.userservice.service;


import com.msa.jrg.core.payload.ApiResponse;
import com.msa.jrg.userservice.config.UserPropertiesConfig;
import com.msa.jrg.userservice.exception.UserNotFoundException;
import com.msa.jrg.userservice.model.User;
import com.msa.jrg.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "userServicer")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPropertiesConfig propertiesConfig;

    public UserServiceImpl(UserRepository userRepository, UserPropertiesConfig propertiesConfig) {
        this.userRepository = userRepository;
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().addAll(users);
        return users;
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        return userRepository.save(domainObject);
    }

    @Override
    @Transactional
    public ApiResponse delete(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.deleteById(id);
            return new ApiResponse(true, propertiesConfig.getUser_delete_apiResponse_message());
        }).orElseThrow(() -> new UserNotFoundException(
                propertiesConfig.getUser_exception_message(),
                propertiesConfig.getUser_resource_name(),
                propertiesConfig.getUser_field_id(), id));

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        propertiesConfig.getUser_exception_message(),
                        propertiesConfig.getUser_resource_name(),
                        propertiesConfig.getUser_field_name(), username)); }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        propertiesConfig.getUser_exception_message(),
                        propertiesConfig.getUser_resource_name(),
                        propertiesConfig.getUser_field_name(), email));
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public List<User> findByIdIn(List<Long> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }


}

