package com.everis.accounts.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bootcamp.commons.models.entity.AccountResponse;

import io.reactivex.Observable;
import io.reactivex.Single;

@RestController
public class AccountsController {
	
	private List<AccountResponse>  listAccounts = Arrays.asList(
			new AccountResponse("1111222233334441XXX", 1000.00),
			new AccountResponse("1111222233334442XXX", 500.00),
			new AccountResponse("1111222233334443XXX", 1500.00)
			);
	
	@GetMapping("/core/accounts")
	public Single<AccountResponse> getAccount(@RequestParam String cardNumber) {
		return Single.fromObservable(
				Observable.fromIterable(listAccounts)
				.filter(account -> account.getAccountNumber().contains(cardNumber))
				)
				.doOnSuccess(response -> Thread.sleep(5000L));
	}
	
}
