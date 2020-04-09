package com.everis.fingerprints.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bootcamp.commons.models.entity.FingerprintResponse;

import io.reactivex.Single;

@RestController
public class FingerPrintController {
	
	@PostMapping("/core/fingerprints/validate")
	public Single<FingerprintResponse> getValidateFingerprint(@RequestBody String document){
		return Single.just(new FingerprintResponse("Core", true));
	}
	
}
