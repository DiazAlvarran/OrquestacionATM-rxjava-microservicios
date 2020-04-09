package com.everis.persons.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.everis.bootcamp.commons.models.entity.PersonResponse;

public interface PersonDao extends CrudRepository<PersonResponse, Integer> {
	
	public PersonResponse findByDocument(String document);
	
}
