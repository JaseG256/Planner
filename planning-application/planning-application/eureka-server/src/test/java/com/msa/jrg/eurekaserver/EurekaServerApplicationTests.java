package com.msa.jrg.eurekaserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EurekaServerApplicationTests {

    @Autowired
    EurekaServerApplication application;

    @Test
    void contextLoads() {
        assertNotNull(application);
    }

}
