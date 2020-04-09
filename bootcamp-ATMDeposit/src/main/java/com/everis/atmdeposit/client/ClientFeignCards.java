package com.everis.atmdeposit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.everis.bootcamp.commons.models.entity.CardResponse;

@FeignClient(name = "Cards", url = "localhost:8084")
public interface ClientFeignCards {
	
	@GetMapping("/core/cards")
	public CardResponse getActiveCard(@RequestParam String documentNumber);
	
}
