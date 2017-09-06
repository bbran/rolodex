package com.libertymutual.goforcode.rolodex.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.junit.Before;
import com.libertymutual.goforcode.rolodex.models.Card;
<<<<<<< HEAD
import com.libertymutual.goforcode.rolodex.repositories.AddressRepo;
=======
import com.libertymutual.goforcode.rolodex.models.EntityNotFoundException;
>>>>>>> 11af448c92174ef36b87e7aea44edd88ec1053fc
import com.libertymutual.goforcode.rolodex.repositories.CardRepo;
import com.libertymutual.goforcode.rolodex.repositories.PhoneNumberRepo;


public class CardApiControllerTests {
	
	private CardRepo cardRepo;
	private AddressRepo addressRepo;
	private PhoneNumberRepo	phoneNumberRepo;
	private CardApiController controller;
	
	@Before
	public void setUp() {
		cardRepo = mock(CardRepo.class);
		addressRepo = mock(AddressRepo.class);
		phoneNumberRepo = mock(PhoneNumberRepo.class);
		controller = new CardApiController(cardRepo, addressRepo, phoneNumberRepo);
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
	
	@Test
	public void test_createNew_creates_and_returns_new_card()	{
		//arrange
		Card card = new Card();
		when(cardRepo.save(card)).thenReturn(card);
		
		//act
		Card cardReturned = controller.createNew(card);
		
		//assert
		assertThat(cardReturned).isSameAs(card);
		verify(cardRepo).save(card);
	}
	
	@Test
	public void test_delete_returns_card_that_was_deleted()	throws EmptyResultDataAccessException {
		//arrange
		Card card = new Card();
		when(cardRepo.findOne(1L)).thenReturn(card);
		
		//act
		Card cardReturned = controller.delete(1L);
		
		//assert
		assertThat(cardReturned).isSameAs(card);
		verify(cardRepo).findOne(1L);
		verify(cardRepo).delete(1L);
	}
	
	@Test
	public void test_delete_returns_EmptyResultDataAccessException_when_card_not_found()	{
		//arrange
		when(cardRepo.findOne(1L)).thenThrow(new EmptyResultDataAccessException(0));
		
		//act
		Card cardReturned = controller.delete(1L);
		
		//assert
		assertThat(cardReturned).isNull();
		verify(cardRepo).findOne(1L);
	}
	
	

}
