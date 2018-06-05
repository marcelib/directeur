package com.wut.directeur;

import com.wut.directeur.config.AppConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan
@RestController
@EnableOAuth2Sso
@SpringBootApplication
public class DirecteurApplication {

    @Value("${dynamic.properties.location}")
    private String dynamicPropertiesLocation;

    public static void main(String[] args) {
        SpringApplication.run(DirecteurApplication.class, args);
    }


    @PostConstruct
    private void initAfterStartup() {
        TimeZone.setDefault(TimeZone.getTimeZone("CET"));
        AppConfig.Companion.setPropertiesLocation(dynamicPropertiesLocation);
    }
}
