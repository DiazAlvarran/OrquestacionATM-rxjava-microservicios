package com.everis.fingerprints;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class BootcampFingerprintsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootcampFingerprintsApplication.class, args);
	}

}
