package com.libertymutual.goforcode.rolodex.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,
property="id")
public class PhoneNumber {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String phoneNumberType;
	
	@Column(nullable=false)
	private String phoneNumber;
	
	@ManyToOne
	private Card card;
	
	public PhoneNumber() {}
	
	public PhoneNumber(String phoneNumberType, String phoneNumber, Card card)	{
		this.phoneNumberType = phoneNumberType;
		this.phoneNumber = phoneNumber;
		this.card = card;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumberType() {
		return phoneNumberType;
	}

	public void setPhoneNumberType(String phoneNumberType) {
		this.phoneNumberType = phoneNumberType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	

}
