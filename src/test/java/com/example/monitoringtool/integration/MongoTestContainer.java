package com.example.monitoringtool.integration;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class MongoTestContainer {

    @Bean
    public GenericContainer<?> mongoContainer() {
        return new GenericContainer<>(DockerImageName.parse("mongo:latest"))
                .withExposedPorts(27017);
    }
}