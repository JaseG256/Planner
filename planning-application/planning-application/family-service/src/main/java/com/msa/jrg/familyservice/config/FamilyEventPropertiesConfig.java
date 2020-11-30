package com.msa.jrg.familyservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "family-event")
public class FamilyEventPropertiesConfig {

    private String family_event_exception_message, family_event_resource_name,
            family_event_field_name, family_event_field_id, family_event_field_title,
            family_event_delete_apiResponse_message, family_event_field_when, family_event_field_place ;
}