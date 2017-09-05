package com.libertymutual.goforcode.rolodex.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String type;
	
	@Column(nullable=true)
	private String street;
	
	@Column(nullable=true)
	private String city;
	
	@Column(nullable=true)
	private String state;
	
	@Column(nullable=true)
	private int zipCode;
	
}
