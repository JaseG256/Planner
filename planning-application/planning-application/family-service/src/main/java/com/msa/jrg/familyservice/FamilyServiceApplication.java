package com.msa.jrg.familyservice;

import com.msa.jrg.familyservice.config.FamilyEventPropertiesConfig;
import com.msa.jrg.familyservice.config.PlacePropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@EnableZuulProxy
@EnableEurekaClient
@EnableConfigurationProperties({PlacePropertiesConfig.class, FamilyEventPropertiesConfig.class})
@SpringBootApplication
public class FamilyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FamilyServiceApplication.class, args);
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
