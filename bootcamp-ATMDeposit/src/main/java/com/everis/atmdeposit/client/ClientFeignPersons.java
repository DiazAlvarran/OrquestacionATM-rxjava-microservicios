package com.everis.atmdeposit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.everis.bootcamp.commons.models.entity.PersonResponse;

@FeignClient(name="Persons", url = "localhost:8081")
public interface ClientFeignPersons {
	
	@GetMapping("/core/persons")
	public PersonResponse showPersonByDocument(@RequestParam String documentNumber);
	
}
