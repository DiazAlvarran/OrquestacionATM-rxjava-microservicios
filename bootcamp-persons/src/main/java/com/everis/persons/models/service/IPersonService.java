package com.everis.persons.models.service;

import com.everis.bootcamp.commons.models.entity.PersonResponse;

import io.reactivex.Single;

public interface IPersonService {
	
	public Single<PersonResponse> findByDocument(String document);
	
}
