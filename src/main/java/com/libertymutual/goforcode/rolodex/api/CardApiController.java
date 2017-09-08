package com.libertymutual.goforcode.rolodex.api;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.rolodex.models.Address;
import com.libertymutual.goforcode.rolodex.models.Card;
import com.libertymutual.goforcode.rolodex.models.EntityNotFoundException;
import com.libertymutual.goforcode.rolodex.models.PhoneNumber;
import com.libertymutual.goforcode.rolodex.repositories.AddressRepo;
import com.libertymutual.goforcode.rolodex.repositories.CardRepo;
import com.libertymutual.goforcode.rolodex.repositories.PhoneNumberRepo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/cards")
public class CardApiController {
	private CardRepo cardRepo;
	private AddressRepo addressRepo;
	private PhoneNumberRepo phoneNumberRepo;
	
	public CardApiController(CardRepo cardRepo, AddressRepo addressRepo, PhoneNumberRepo phoneNumberRepo)	{
		this.cardRepo = cardRepo;
		this.addressRepo = addressRepo;
		this.phoneNumberRepo = phoneNumberRepo;
		
		Card card1 = new Card("Steve1", "Jones", "The Boss", "Liberty Mutual", "https://www.libertymutual.com/");
		Card card2 = new Card("Steve2", "Jones", "The Boss", "Liberty Mutual", "https://www.libertymutual.com/");
		Card card3 = new Card("Steve3", "Jones", "The Boss", "Liberty Mutual", "https://www.libertymutual.com/");
		cardRepo.save(card1);
		cardRepo.save(card2);
		cardRepo.save(card3);
		Address address1 = new Address("Home", "123 Fake St.", "Fakesville", "DE", 12345, card1);
		Address address2 = new Address("Home", "456 Fake St.", "Fakesville", "DE", 12345, card2);
		Address address3 = new Address("Home", "567 Fake St.", "Fakesville", "DE", 12345, card3);
		Address address4 = new Address("Work", "100 Main St.", "Dover", "NH", 12345, card1);
		addressRepo.save(address1);
		addressRepo.save(address2);
		addressRepo.save(address3);
		addressRepo.save(address4);
		PhoneNumber phoneNumber1 = new PhoneNumber("Work", "1234567", card1);
		PhoneNumber phoneNumber4 = new PhoneNumber("Cell", "6665210", card1);
		PhoneNumber phoneNumber2 = new PhoneNumber("Work", "9876543210", card2);
		PhoneNumber phoneNumber3 = new PhoneNumber("Work", "18888675309", card3);
		phoneNumberRepo.save(phoneNumber1);
		phoneNumberRepo.save(phoneNumber2);
		phoneNumberRepo.save(phoneNumber3);
		phoneNumberRepo.save(phoneNumber4);
	}
	
	@GetMapping("")
	public List<Card> getAll(String lastName)	{
		if (lastName != null) {
			return cardRepo.findByLastName(lastName);
		} 
		return cardRepo.findAll();
		
	}
	
	@GetMapping("{id}")
	public Card getOne(@PathVariable long id) throws EntityNotFoundException	{
		Card card = cardRepo.findOne(id);
		if (card == null) {
			throw new EntityNotFoundException();
		}
		return card;
	}
	
	@PostMapping("")
	public Card createNew(@RequestBody Card card)	{
		return cardRepo.save(card);
	}
	
	@DeleteMapping("{id}")
	public Card delete(@PathVariable long id) {
		try {
			Card card = cardRepo.findOne(id);
			cardRepo.delete(id);
			return card;
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}

	@PutMapping("{id}")
	public Card update(@RequestBody Card card, @PathVariable long id) {
		card.setId(id);
		return cardRepo.save(card);
	}
	
	
	@PostMapping("{id}/addresses")
	public Card createAddressForCard(@PathVariable long id, @RequestBody Address address)	{
		Card card = cardRepo.findOne(id);
		address.setCard(card);			
		addressRepo.save(address);
		return card;
	}

	@PostMapping("{id}/phones")
	public Card createPhoneNumberForCard(@PathVariable long id, @RequestBody PhoneNumber phoneNumber)	{
		Card card = cardRepo.findOne(id);
		phoneNumber.setCard(card);	
		phoneNumberRepo.save(phoneNumber);
		return card;
	}
	
	@DeleteMapping("{id}/addresses/{add_id}")
	public Card deleteAddress(@PathVariable long id, @PathVariable long add_id) {
		try {
			Card card = cardRepo.findOne(id);
			Address address = addressRepo.findOne(add_id);
			addressRepo.delete(address);
			return card;
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
	@DeleteMapping("{id}/phones/{pho_id}")
	public Card deletePhone(@PathVariable long id, @PathVariable long pho_id) {
		try {
			Card card = cardRepo.findOne(id);
			PhoneNumber phoneNumber = phoneNumberRepo.findOne(pho_id);
			phoneNumberRepo.delete(phoneNumber);
			return card;
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
	@ApiOperation(notes="In the address object body, either pass an embedded card object, or omit the card attribute entirely", value = "Update Address")
	@PutMapping("{id}/addresses/{add_id}")
	public Address updateAddress(@RequestBody Address address, @PathVariable long id, @PathVariable long add_id)	{
		address.setId(add_id);
		address.setCard(cardRepo.findOne(id));
		return addressRepo.save(address);
	}
	
	@ApiOperation(notes="In the phoneNumber object body, either pass an embedded card object, or omit the card attribute entirely", value = "Update PhoneNumber")
	@PutMapping("{id}/phones/{pho_id}")
	public PhoneNumber updatePhone(@RequestBody PhoneNumber phoneNumber, @PathVariable long id, @PathVariable long pho_id)	{
		phoneNumber.setId(pho_id);
		phoneNumber.setCard(cardRepo.findOne(id));
		return phoneNumberRepo.save(phoneNumber);
	}

}
