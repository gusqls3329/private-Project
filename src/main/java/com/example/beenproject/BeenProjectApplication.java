package com.example.beenproject;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ConfigurationPropertiesScan
@EnableConfigurationProperties
@EnableJpaRepositories
@EnableJpaAuditing
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BeenProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeenProjectApplication.class, args);
	}

}
