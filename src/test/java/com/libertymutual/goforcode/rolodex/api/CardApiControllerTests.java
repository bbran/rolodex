package com.libertymutual.goforcode.rolodex.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import com.libertymutual.goforcode.rolodex.models.Card;
import com.libertymutual.goforcode.rolodex.models.EntityNotFoundException;
import com.libertymutual.goforcode.rolodex.repositories.CardRepo;


public class CardApiControllerTests {
	
	private CardRepo cardRepo;
	private CardApiController controller;
	
	@Before
	public void setUp() {
		cardRepo = mock(CardRepo.class);
//		controller = new CardApiController(cardRepo);
	}
	
	
	@Test
	public void testing_to_make_sure_getAll_returns_all_cards() {
		//arrange
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card());
		cards.add(new Card());
		
		when(cardRepo.findAll()).thenReturn(cards);
		
		//act
		List<Card> actual = controller.getAll();
		
		//assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(cards.get(0));
		verify(cardRepo).findAll();
	}
	
	
	@Test
	public void test_to_ensure_that_getOne_returns_one_card() throws EntityNotFoundException {
		//arrange
		Card randomCard = new Card();
		when(cardRepo.findOne(4l)).thenReturn(randomCard);
				
		//act
		Card actual = controller.getOne(4l);
				
		//assert
		assertThat(actual).isEqualTo(randomCard);
		verify(cardRepo).findOne(4l);
	}
	
	

}
