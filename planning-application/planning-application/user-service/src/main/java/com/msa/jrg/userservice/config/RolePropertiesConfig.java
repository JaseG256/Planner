package com.msa.jrg.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "role")
public class RolePropertiesConfig {

    private String role_field_id, role_field_name, role_resource_name, role_delete_apiResponse_message,
    role_exception_message;
}
