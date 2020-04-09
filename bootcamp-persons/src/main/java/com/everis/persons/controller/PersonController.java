package com.everis.persons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bootcamp.commons.models.entity.PersonResponse;
import com.everis.persons.models.service.IPersonService;

import io.reactivex.Single;

@RestController
public class PersonController {
	
	@Autowired
	private IPersonService personService;
	
	@GetMapping("/core/persons")
	public Single<PersonResponse> showPersonByDocument(@RequestParam String documentNumber) {
		return personService.findByDocument(documentNumber);
	}
	
}
