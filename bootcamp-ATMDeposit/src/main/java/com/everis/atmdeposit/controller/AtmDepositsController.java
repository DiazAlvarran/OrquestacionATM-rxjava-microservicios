package com.everis.atmdeposit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bootcamp.commons.models.entity.AccountResponse;
import com.everis.bootcamp.commons.models.entity.PersonValidate;
import com.everis.atmdeposit.models.service.IATMDepositService;

import io.reactivex.Observable;
import io.reactivex.Single;

@RestController
public class AtmDepositsController {

	private static final Logger log = LoggerFactory.getLogger(AtmDepositsController.class);

	@Autowired
	private IATMDepositService service;

	@PostMapping("/atm/deposits")
	public Single<?> toDeposit(@RequestBody Map<String, Object> body) {
		Map<String, Object> response = new HashMap<>();
		return service.callPersonService(body.get("documentNumber").toString())
				.map(person -> {
					if (person.getId() == null) return new ClassNotFoundException("Person not found");
					if (person.getBlackList()) return new IllegalAccessException("The person is in the Black List");
					validateFingerprintOrReniec(person.getFingerPrint(), person.getDocument()) //to call fingerprint o reniec
						.filter(validate -> validate.getSuccess())
						.doOnSuccess(validate -> {
							service.callCardService(person.getDocument())
								.map(cardResponse -> cardResponse.getCards() //convert cardResponse to accountResponse list
									.parallelStream()
									.map(card -> service.callAccountService(card.getCardNumber()).blockingGet())
									.peek(accountResponse -> log.info("DOCUMENT NUMBER: " + person.getDocument() + " - ACCOUNT: " + accountResponse.getAccountNumber()))
									.collect(Collectors.toList())
								)
								.doOnSuccess(accountList->{
									response.put("fingerprintEntityName", validate.getEntityName());
									response.put("validAccounts", validAccountsResponse(accountList));
									response.put("customerAmount", customerAmountResponse(accountList, Double.parseDouble(body.get("amount").toString())));
								})
								.subscribe();
						}).subscribe();
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				});
	}
	
	public Single<PersonValidate> validateFingerprintOrReniec(Boolean fingerprint, String document) {
		if(fingerprint) return service.callFingerprintService(document)
				.map(finger -> new PersonValidate(finger.getEntityName(), finger.getSuccess()));
		return service.callReniecService(document)
				.map(reniec -> new PersonValidate(reniec.getEntityName(), reniec.getSuccess()));
	}
	
	public List<Map<String, Object>> validAccountsResponse(List<AccountResponse> accountList) {
		return Observable.fromIterable(accountList)
				.map(account -> {
					Map<String, Object> accountMap = new HashMap<String, Object>();
					accountMap.put("accountNumber", account.getAccountNumber());
					return accountMap;
				})
				.toList().blockingGet();
	}
	
	public Double customerAmountResponse(List<AccountResponse> accountList, Double newAmount) {
		return accountList.stream()
				.map(AccountResponse::getAmount)
				.reduce((total, amount) -> total+amount).get() + newAmount;
	}
}
