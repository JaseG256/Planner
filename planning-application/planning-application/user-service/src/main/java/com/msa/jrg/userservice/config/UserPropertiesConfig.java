package com.msa.jrg.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "user")
public class UserPropertiesConfig {

    private String user_delete_apiResponse_message, user_resource_name, user_field_name, user_field_id, user_field_email,
            user_exception_message;
}
