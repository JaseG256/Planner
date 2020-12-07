package com.msa.jrg.userservice.service;


import com.msa.jrg.core.payload.ApiResponse;
import com.msa.jrg.userservice.config.UserPropertiesConfig;
import com.msa.jrg.userservice.exception.UserNotFoundException;
import com.msa.jrg.userservice.model.User;
import com.msa.jrg.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "userServicer")
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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

    @Cacheable(value = "user")
    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @CacheEvict(value = "user", key = "#user.getId()")
    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "user", key = "#id")
    @Override
    @Transactional
    public ApiResponse delete(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.deleteById(id);
            return new ApiResponse(true, propertiesConfig.getUser_delete_apiResponse_message());
        }).orElseThrow(() -> getUserNotFoundException(id));
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> getUserNotFoundException(username)); }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> getUserNotFoundException(email));
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

    @Override
    public ApiResponse uploadFile(Long userId, MultipartFile file) {
        return null;
    }

    @Override
    public UserPropertiesConfig propertyConfig() {
        return this.propertiesConfig;
    }

    private UserNotFoundException getUserNotFoundException(Object fieldValue) {
        String msg = logException(fieldValue);
        return new UserNotFoundException(
                msg, propertiesConfig.getUser_resource_name(), propertiesConfig.getUser_field_id(), fieldValue
        );
    }

    private String logException(Object fieldValue) {
        String msg = String.format(propertiesConfig.getUser_exception_message(), fieldValue);
        logger.debug(msg);
        return msg;
    }


}

