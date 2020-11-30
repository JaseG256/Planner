package com.msa.jrg.familyservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "address")
public class AddressConfigurationProperties {

    private String defaultCountry;
}
