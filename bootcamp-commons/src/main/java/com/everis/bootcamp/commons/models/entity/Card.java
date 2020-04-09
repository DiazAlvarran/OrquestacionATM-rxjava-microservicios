package com.everis.bootcamp.commons.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

	private String cardNumber;
	private Boolean active;

}
