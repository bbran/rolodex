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
import com.libertymutual.goforcode.rolodex.models.PhoneNumber;
import com.libertymutual.goforcode.rolodex.repositories.AddressRepo;
import com.libertymutual.goforcode.rolodex.repositories.CardRepo;
import com.libertymutual.goforcode.rolodex.repositories.PhoneNumberRepo;

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
		
		Card card1 = new Card("Steve1", "Jones", "The Boss", "Liberty Mutual");
		Card card2 = new Card("Steve2", "Jones", "The Boss", "Liberty Mutual");
		Card card3 = new Card("Steve3", "Jones", "The Boss", "Liberty Mutual");
		cardRepo.save(card1);
		cardRepo.save(card2);
		cardRepo.save(card3);
		Address address1 = new Address("Home", "123 Fake St.", "Fakesville", "DE", 12345, card1);
		Address address2 = new Address("Home", "456 Fake St.", "Fakesville", "DE", 12345, card2);
		Address address3 = new Address("Home", "567 Fake St.", "Fakesville", "DE", 12345, card3);
		addressRepo.save(address1);
		addressRepo.save(address2);
		addressRepo.save(address3);
		PhoneNumber phoneNumber1 = new PhoneNumber("Work", 1234567L, card1);
		PhoneNumber phoneNumber2 = new PhoneNumber("Work", 9876543210L, card2);
		PhoneNumber phoneNumber3 = new PhoneNumber("Work", 18888675309L, card3);
		phoneNumberRepo.save(phoneNumber1);
		phoneNumberRepo.save(phoneNumber2);
		phoneNumberRepo.save(phoneNumber3);
	}
	
	@GetMapping("")
	public List<Card> getAll()	{
		return cardRepo.findAll();
	}
	
	@GetMapping("{id}")
	public Card getOne(@PathVariable long id)	{
		Card card = cardRepo.findOne(id);
		if (card == null) {
			//TODO we need an exception here
			//			return null;
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
					
		addressRepo.save(address);
		return card;
	}

	@PostMapping("{id}/phones")
	public Card createPhoneNumberForCard(@PathVariable long id, @RequestBody PhoneNumber phoneNumber)	{
		Card card = cardRepo.findOne(id);
		phoneNumber = phoneNumberRepo.findOne(phoneNumber.getId());
		
			
		phoneNumberRepo.save(phoneNumber);
		return card;
	}
	
	
	

}