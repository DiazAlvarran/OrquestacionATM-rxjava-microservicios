package com.everis.atmdeposit.models.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.atmdeposit.client.ClientFeignAccounts;
import com.everis.atmdeposit.client.ClientFeignCards;
import com.everis.atmdeposit.client.ClientFeignFingerprints;
import com.everis.atmdeposit.client.ClientFeignPersons;
import com.everis.atmdeposit.client.ClientFeignReniec;
import com.everis.bootcamp.commons.models.entity.AccountResponse;
import com.everis.bootcamp.commons.models.entity.CardResponse;
import com.everis.bootcamp.commons.models.entity.FingerprintResponse;
import com.everis.bootcamp.commons.models.entity.PersonResponse;
import com.everis.bootcamp.commons.models.entity.ReniecResponse;

import io.reactivex.Single;

@Service
public class ATMDepositServiceImpl implements IATMDepositService{
	
	@Autowired
	private ClientFeignPersons feignPersons;
	
	@Autowired
	private ClientFeignFingerprints feignFingerprints;
	
	@Autowired
	private ClientFeignReniec feignReniec;
	
	@Autowired
	private ClientFeignCards feignCards;
	
	@Autowired
	private ClientFeignAccounts feignAccounts;
	
	@Override
	public Single<PersonResponse> callPersonService(String documentNumber) {
		return Single.just(feignPersons.showPersonByDocument(documentNumber));
	}

	@Override
	public Single<FingerprintResponse> callFingerprintService(String document) {
		return Single.just(feignFingerprints.getValidateFingerprint(document));
	}

	@Override
	public Single<ReniecResponse> callReniecService(String document) {
		return Single.just(feignReniec.getValidateReniec(document));
	}

	@Override
	public Single<CardResponse> callCardService(String documentNumber) {
		return Single.just(new CardResponse(feignCards.getActiveCard(documentNumber).getCards().stream()
				.filter(card -> card.getActive())
				.collect(Collectors.toList())
				));
	}

	@Override
	public Single<AccountResponse> callAccountService(String cardNumber) {
		return Single.just(feignAccounts.getAccount(cardNumber));
	}

}
