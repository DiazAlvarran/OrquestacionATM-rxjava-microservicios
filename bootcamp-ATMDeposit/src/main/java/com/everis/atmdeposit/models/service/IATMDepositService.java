package com.everis.atmdeposit.models.service;

import com.everis.bootcamp.commons.models.entity.AccountResponse;
import com.everis.bootcamp.commons.models.entity.CardResponse;
import com.everis.bootcamp.commons.models.entity.FingerprintResponse;
import com.everis.bootcamp.commons.models.entity.PersonResponse;
import com.everis.bootcamp.commons.models.entity.ReniecResponse;

import io.reactivex.Single;

public interface IATMDepositService {

	public Single<PersonResponse> callPersonService(String documentNumber);

	public Single<FingerprintResponse> callFingerprintService(String document);

	public Single<ReniecResponse> callReniecService(String document);

	public Single<CardResponse> callCardService(String documentNumber);

	public Single<AccountResponse> callAccountService(String cardNumber);
}
