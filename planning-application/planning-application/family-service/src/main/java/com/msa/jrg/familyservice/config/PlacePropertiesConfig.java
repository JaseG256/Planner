package com.msa.jrg.familyservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "place")
public class PlacePropertiesConfig {

    private String place_field_id, place_field_name, place_resource_name, place_delete_apiResponse_message,
            place_exception_message, place_field_location;
}
