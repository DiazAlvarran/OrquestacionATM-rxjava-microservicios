package com.everis.bootcamp.commons.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String document;

	@Column(name = "finger_print")
	private Boolean fingerPrint;

	@Column(name = "black_list")
	private Boolean blackList;

	private static final long serialVersionUID = 1L;

}
