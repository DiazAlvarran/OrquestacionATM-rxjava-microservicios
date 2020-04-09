package com.everis.atmdeposit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.everis.bootcamp.commons.models.entity.AccountResponse;

@FeignClient(name = "Accounts", url = "localhost:8085")
public interface ClientFeignAccounts {

	@GetMapping("/core/accounts")
	public AccountResponse getAccount(@RequestParam String cardNumber);

}
