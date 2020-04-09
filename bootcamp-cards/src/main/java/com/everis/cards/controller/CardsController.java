package com.everis.cards.controller;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bootcamp.commons.models.entity.Card;
import com.everis.bootcamp.commons.models.entity.CardResponse;

import io.reactivex.Single;

@RestController
public class CardsController {
	
	@GetMapping("/core/cards")
	public Single<CardResponse> getActiveCard(@RequestParam String documentNumber){		
		return Single.just(new CardResponse(Arrays.asList(
				new Card("1111222233334441", true),
				new Card("1111222233334442", true),
				new Card("1111222233334443", true),
				new Card("1111222233334444", false),
				new Card("1111222233334445", false),
				new Card("1111222233334446", false)
			)));
	}
	
}
