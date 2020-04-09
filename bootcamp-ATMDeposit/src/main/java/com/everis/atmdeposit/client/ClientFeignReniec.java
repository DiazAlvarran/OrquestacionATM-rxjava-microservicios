package com.everis.atmdeposit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.everis.bootcamp.commons.models.entity.ReniecResponse;

@FeignClient(name = "Reniec", url = "localhost:8083")
public interface ClientFeignReniec {
	
	@PostMapping("/external/reniec/validate")
	public ReniecResponse getValidateReniec(@RequestBody String document);

}
