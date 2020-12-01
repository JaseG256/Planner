package com.msa.jrg.familyservice.config;

import com.msa.jrg.familyservice.repository.FamilyEventRepository;
import com.msa.jrg.familyservice.repository.PlaceRepository;
import com.msa.jrg.familyservice.service.FamilyEventService;
import com.msa.jrg.familyservice.service.FamilyEventServiceImpl;
import com.msa.jrg.familyservice.service.PlaceService;
import com.msa.jrg.familyservice.service.PlaceServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@Profile("test")
@PropertySource("classpath:application-unitTest.properties")
@Configuration
public class ServiceUnitTestConfig {

    @Bean
    @Primary
    public PlacePropertiesConfig placePropertiesConfig() {
        return new PlacePropertiesConfig();
    }

    @Bean
    @Primary
    public FamilyEventPropertiesConfig familyEventPropertiesConfig() {
        return new FamilyEventPropertiesConfig();
    }

    @Bean
    @Primary
    public PlaceRepository placeRepository() {
        return Mockito.mock(PlaceRepository.class);
    }

    @Bean
    @Primary
    public PlaceService placeService() {
        return new PlaceServiceImpl(placeRepository(), placePropertiesConfig());
    }

    @Bean(name = "placeTwo")
    public PlaceService placeServiceTwo() {
        return Mockito.mock(PlaceService.class);
    }

    @Bean
    @Primary
    public FamilyEventRepository familyEventRepository() {
        return Mockito.mock(FamilyEventRepository.class);
    }

    @Bean
    @Primary
    public FamilyEventService eventService() {
        return new FamilyEventServiceImpl(familyEventRepository(), familyEventPropertiesConfig(), placeServiceTwo());
    }
}
