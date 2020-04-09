package com.everis.atmdeposit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.everis.bootcamp.commons.models.entity.FingerprintResponse;

@FeignClient(name="Fingerprints", url = "localhost:8082")
public interface ClientFeignFingerprints {
	
	@PostMapping("/core/fingerprints/validate")
	public FingerprintResponse getValidateFingerprint(@RequestBody String document);
	
}
