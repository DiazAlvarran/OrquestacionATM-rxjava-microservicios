package com.everis.reniec.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bootcamp.commons.models.entity.ReniecResponse;

import io.reactivex.Single;

@RestController
public class ReniecController {
	
	@PostMapping("/external/reniec/validate")
	public Single<ReniecResponse> getValidateReniec(@RequestBody String document){
		return Single.just(new ReniecResponse("Reniec", true));
	}
	
}
