package com.example.beenproject;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@EnableJpaAuditing //  자동으로 updateAt, createAt을 하려면 @EntityListeners(AuditingEntityListener.class) 을 같이 사용해 주어야함
@ConfigurationPropertiesScan
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BeenProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeenProjectApplication.class, args);
	}

}
