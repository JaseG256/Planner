package com.msa.jrg.userservice.config;

import com.msa.jrg.userservice.repository.RoleRepository;
import com.msa.jrg.userservice.repository.UserRepository;
import com.msa.jrg.userservice.service.RoleService;
import com.msa.jrg.userservice.service.RoleServiceImpl;
import com.msa.jrg.userservice.service.UserService;
import com.msa.jrg.userservice.service.UserServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@Profile("test")
@PropertySource("classpath:application-unitTest.properties")
@Configuration
public class ServiceUnitTestConfig {

    @Bean
    @Primary
    public RolePropertiesConfig rolePropertiesConfig() {
        return new RolePropertiesConfig();
    }

    @Bean
    @Primary
    public UserPropertiesConfig userPropertiesConfig() {
        return new UserPropertiesConfig();
    }

    @Bean
    @Primary
    public RoleRepository roleRepository() {
        return Mockito.mock(RoleRepository.class);
    }

    @Bean
    @Primary
    public RoleService roleService() {
        return new RoleServiceImpl(roleRepository(), rolePropertiesConfig());
    }

    @Bean
    @Primary
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    @Primary
    public UserService userService() {
        return new UserServiceImpl(userRepository(), userPropertiesConfig());
    }

}
