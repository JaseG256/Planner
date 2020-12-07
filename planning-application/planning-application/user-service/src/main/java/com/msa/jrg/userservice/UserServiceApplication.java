package com.msa.jrg.userservice;

import com.msa.jrg.userservice.config.RolePropertiesConfig;
import com.msa.jrg.userservice.config.UserPropertiesConfig;
import com.msa.jrg.userservice.config.WebClientPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@EnableCaching
@EnableEurekaClient
@EnableConfigurationProperties({
        RolePropertiesConfig.class, UserPropertiesConfig.class, WebClientPropertiesConfig.class
})
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
