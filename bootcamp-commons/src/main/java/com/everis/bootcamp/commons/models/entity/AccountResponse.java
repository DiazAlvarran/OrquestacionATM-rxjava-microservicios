package com.everis.bootcamp.commons.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

	private String accountNumber;
	private Double amount;

}
