package com.libertymutual.goforcode.rolodex.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.rolodex.models.Card;
import com.libertymutual.goforcode.rolodex.repositories.CardRepo;

@RestController
@RequestMapping("/api/cards")
public class CardApiContoller {
	private CardRepo cardRepo;
	
	public CardApiContoller(CardRepo cardRepo)	{
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

}
