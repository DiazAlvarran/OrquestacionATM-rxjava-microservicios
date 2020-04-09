package com.everis.bootcamp.commons.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonValidate {

	private String entityName;
	private Boolean success;

}
