package com.msa.jrg.userservice.config;

import com.msa.jrg.userservice.client.DBFileRestClient;
import com.msa.jrg.userservice.client.DiscoveryService;
import com.msa.jrg.userservice.repository.RoleRepository;
import com.msa.jrg.userservice.repository.UserRepository;
import com.msa.jrg.userservice.service.RoleService;
import com.msa.jrg.userservice.service.RoleServiceImpl;
import com.msa.jrg.userservice.service.UserService;
import com.msa.jrg.userservice.service.UserServiceImpl;
import org.mockito.Mockito;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

@Profile("test")
@PropertySource("classpath:application-unitTest.properties")
@Configuration
public class ServiceUnitTestConfig {

    @Primary
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    public LoadBalancerClient loadBalancerClient() {
        return Mockito.mock(LoadBalancerClient.class);
    }

    @Bean
    @Primary
    public DiscoveryClient discoveryClient() {
        return Mockito.mock(DiscoveryClient.class);
    }

    @Bean
    @Primary
    public DiscoveryService discoveryService() {
        return new DiscoveryService(loadBalancerClient(), discoveryClient());
    }

    @Bean
    @Primary
    public WebClientPropertiesConfig webClientPropertiesConfig() {
        return new WebClientPropertiesConfig();
    }

    @Bean
    @Primary
    public DBFileRestClient dbFileRestClient() {
        return new DBFileRestClient(restTemplate(), discoveryService(), webClientPropertiesConfig());
    }

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
