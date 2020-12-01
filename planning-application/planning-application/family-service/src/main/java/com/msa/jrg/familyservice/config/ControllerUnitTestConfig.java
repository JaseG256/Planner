package com.msa.jrg.familyservice.config;

import com.msa.jrg.familyservice.resources.FamilyEventRestController;
import com.msa.jrg.familyservice.service.FamilyEventService;
import com.msa.jrg.familyservice.service.FamilyEventServiceImpl;
import com.msa.jrg.familyservice.service.PlaceService;
import com.msa.jrg.familyservice.service.PlaceServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("controller_test")
@Configuration
public class ControllerUnitTestConfig {

    @Bean
    @Primary
    public FamilyEventPropertiesConfig familyEventPropertiesConfig() {
        return new FamilyEventPropertiesConfig();
    }

    @Bean
    @Primary
    public PlaceService placeService() {
        return Mockito.mock(PlaceServiceImpl.class);
    }

    @Bean
    @Primary
    public FamilyEventService familyEventService() {
        return Mockito.mock(FamilyEventServiceImpl.class);
    }

    @Bean
    @Primary
    public FamilyEventRestController familyEventController() {
        return new FamilyEventRestController(familyEventService(), placeService());
    }

}
