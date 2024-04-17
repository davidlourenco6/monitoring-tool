package com.example.monitoringtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MonitoringToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringToolApplication.class, args);
	}

}
