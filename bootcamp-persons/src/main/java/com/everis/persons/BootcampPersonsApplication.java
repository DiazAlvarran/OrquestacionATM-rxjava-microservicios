package com.everis.persons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.everis.bootcamp.commons.models.entity"})
public class BootcampPersonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootcampPersonsApplication.class, args);
	}

}
