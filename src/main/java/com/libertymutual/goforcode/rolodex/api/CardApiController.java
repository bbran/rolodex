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
import com.libertymutual.goforcode.rolodex.models.Card;
import com.libertymutual.goforcode.rolodex.repositories.CardRepo;

@RestController
@RequestMapping("/api/cards")
public class CardApiController {
	private CardRepo cardRepo;
	
	public CardApiController(CardRepo cardRepo)	{
		this.cardRepo = cardRepo;
		
		cardRepo.save(new Card("Steve1", "Jones", "The Boss", "Liberty Mutual"));
		cardRepo.save(new Card("Steve2", "Jones", "The Boss", "Liberty Mutual"));
		cardRepo.save(new Card("Steve3", "Jones", "The Boss", "Liberty Mutual"));
		cardRepo.save(new Card("Steve4", "Jones", "The Boss", "Liberty Mutual"));
		cardRepo.save(new Card("Steve5", "Jones", "The Boss", "Liberty Mutual"));
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

}
