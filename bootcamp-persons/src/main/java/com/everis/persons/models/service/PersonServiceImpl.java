package com.everis.persons.models.service;

import static java.util.Optional.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.persons.models.dao.PersonDao;
import com.everis.bootcamp.commons.models.entity.PersonResponse;

import io.reactivex.Single;

@Service
public class PersonServiceImpl implements IPersonService{
	
	@Autowired
	private PersonDao personDao;
	
	@Override
	@Transactional(readOnly = true)
	public Single<PersonResponse> findByDocument(String document) {
		return Single.just(ofNullable(personDao.findByDocument(document)).orElse(new PersonResponse()));
	}

}
