package com.msa.jrg.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "webclient")
public class WebClientPropertiesConfig {

    private String baseUrl, uploadFileUrl, fileName, fileType, data, getFile;
}
