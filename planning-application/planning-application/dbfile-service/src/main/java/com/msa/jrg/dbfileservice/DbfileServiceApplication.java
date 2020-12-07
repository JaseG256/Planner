package com.msa.jrg.dbfileservice;

import com.msa.jrg.dbfileservice.config.DBFilePropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(DBFilePropertiesConfig.class)
@SpringBootApplication
public class DbfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbfileServiceApplication.class, args);
    }

}
