package com.libertymutual.goforcode.rolodex.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.junit.Before;

import com.libertymutual.goforcode.rolodex.models.Address;
import com.libertymutual.goforcode.rolodex.models.Card;
import com.libertymutual.goforcode.rolodex.repositories.AddressRepo;
import com.libertymutual.goforcode.rolodex.models.EntityNotFoundException;
import com.libertymutual.goforcode.rolodex.models.PhoneNumber;
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
	public void test_updateAddress_returns_address_with_passed_address_id_and_card()	{
		//arrange
		Card card = new Card();
		Address address = new Address();
		when(cardRepo.findOne(1L)).thenReturn(card);
		when(addressRepo.save(address)).thenReturn(address);
		
		//act
		Address addressReturned = controller.updateAddress(address, 1L, 2L);
		
		//assert
		assertThat(addressReturned).isSameAs(address);
		assertThat(addressReturned.getId()).isEqualTo(2L);
		assertThat(addressReturned.getCard()).isSameAs(card);
		verify(cardRepo).findOne(1L);
		verify(addressRepo).save(address);
	}
	
	@Test
	public void test_updatePhone_returns_phones_with_passed_phone_id_and_card()	{
		//arrange
		Card card = new Card();
		PhoneNumber phoneNumber = new PhoneNumber();
		when(cardRepo.findOne(1L)).thenReturn(card);
		when(phoneNumberRepo.save(phoneNumber)).thenReturn(phoneNumber);
		
		//act
		PhoneNumber phoneNumberReturned = controller.updatePhone(phoneNumber, 1L, 2L);
		
		//assert
		assertThat(phoneNumberReturned).isSameAs(phoneNumber);
		assertThat(phoneNumberReturned.getId()).isEqualTo(2L);
		assertThat(phoneNumberReturned.getCard()).isSameAs(card);
		verify(cardRepo).findOne(1L);
		verify(phoneNumberRepo).save(phoneNumber);
	}
	
	@Test
	public void test_deletePhoneNumber_returns_card_from_which_phone_number_was_deleted()	{
		//arrange
		Card card = new Card();
		PhoneNumber phoneNumber = new PhoneNumber();
		
		when(cardRepo.findOne(1L)).thenReturn(card);
		when(phoneNumberRepo.findOne(2L)).thenReturn(phoneNumber);
		
		//act
		Card cardReturned = controller.deletePhone(1L, 2L);
		
		//
		assertThat(cardReturned).isSameAs(card);
		verify(cardRepo).findOne(1L);
		verify(phoneNumberRepo).findOne(2L);
		verify(phoneNumberRepo).delete(phoneNumber);
	}
	
	@Test
	public void test_deletePhone_returns_null_when_phone_number_not_found_and_EmptyResultDataAccessException_is_thrown()	{
		//arrange
		when(phoneNumberRepo.findOne(2L)).thenThrow(new EmptyResultDataAccessException(0));
		
		//act
		Card cardReturned = controller.deletePhone(1L, 2L);
		
		//assert
		assertThat(cardReturned).isNull();
		verify(cardRepo).findOne(1L);
		verify(phoneNumberRepo).findOne(2L);
	}
	
	@Test
	public void test_deleteAddress_returns_card_from_which_address_was_deleted()	{
		//arrange
		Card card = new Card();
		Address address = new Address();
		
		when(cardRepo.findOne(1L)).thenReturn(card);
		when(addressRepo.findOne(2L)).thenReturn(address);
		
		//act
		Card cardReturned = controller.deleteAddress(1L, 2L);
		
		//
		assertThat(cardReturned).isSameAs(card);
		verify(cardRepo).findOne(1L);
		verify(addressRepo).findOne(2L);
		verify(addressRepo).delete(address);
	}
	
	@Test
	public void test_deleteAddress_returns_null_when_address_not_found_and_EmptyResultDataAccessException_is_thrown()	{
		//arrange
		when(addressRepo.findOne(2L)).thenThrow(new EmptyResultDataAccessException(0));
		
		//act
		Card cardReturned = controller.deleteAddress(1L, 2L);
		
		//assert
		assertThat(cardReturned).isNull();
		verify(cardRepo).findOne(1L);
		verify(addressRepo).findOne(2L);
	}
	
	@Test
	public void test_createAddressForCard_returns_same_card_associated_with_new_address()	{
		//arrange
		Card card = new Card();
		Address address = new Address();
		when(cardRepo.findOne(1L)).thenReturn(card);
		
		//act
		Card cardReturned = controller.createAddressForCard(1L, address);
		
		//assert
		assertThat(cardReturned).isSameAs(address.getCard());
		verify(cardRepo).findOne(1L);
		verify(addressRepo).save(address);
	}
	
	@Test
	public void test_createPhoneNumberForCard_returns_same_card_associated_with_new_phone_number()	{
		//arrange
		Card card = new Card();
		PhoneNumber phoneNumber = new PhoneNumber();
		when(cardRepo.findOne(1L)).thenReturn(card);
		
		//act
		Card cardReturned = controller.createPhoneNumberForCard(1L, phoneNumber);
		
		//assert
		assertThat(cardReturned).isSameAs(phoneNumber.getCard());
		verify(cardRepo).findOne(1L);
		verify(phoneNumberRepo).save(phoneNumber);
	}
	
	@Test
	public void test_update_returns_card_with_passed_id_value()	{
		//arrange
		Card card1 = new Card();
		
		when(cardRepo.save(card1)).thenReturn(card1);
		
		//act
		Card cardReturned = controller.update(card1, 1L);
		
		//assert
		assertThat(cardReturned).isSameAs(card1);
		assertThat(cardReturned.getId()).isEqualTo(1L);
		verify(cardRepo).save(card1);
	}
	
	
	@Test
	public void testing_to_make_sure_getAll_returns_all_cards_if_lastName_is_null() {
		//arrange
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card());
		cards.add(new Card());
		
		when(cardRepo.findAll()).thenReturn(cards);
		
		//act
		List<Card> actual = controller.getAll(null);
		
		//assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(cards.get(0));
		verify(cardRepo).findAll();
	}
	
	@Test
	public void testing_to_make_sure_getAll_returns_result_of_last_name_query_if_lastName_is_not_null() {
		//arrange
		Card card1 = new Card();
		Card card2 = new Card();
		ArrayList<Card> cards1 = new ArrayList<Card>();
		ArrayList<Card> cards2 = new ArrayList<Card>();
		cards1.add(card1);
		cards1.add(card2);
		cards2.add(card1);
		
		when(cardRepo.findByLastName("test")).thenReturn(cards2);
		
		//act
		List<Card> actual = controller.getAll("test");
		
		//assert
		assertThat(actual.size()).isEqualTo(1);
		assertThat(actual.get(0)).isSameAs(cards1.get(0));
		verify(cardRepo).findByLastName("test");
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
		public void test_to_ensure_that_getOne_throws_EntityNotFoundException_if_card_not_found() {
			try	{
				controller.getOne(4L);
				fail("Exception not thrown");
			} catch (EntityNotFoundException e)	{
				
			}
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
	public void test_delete_returns_null_when_card_not_found_and_EmptyResultDataAccessException_is_thrown()	{
		//arrange
		when(cardRepo.findOne(1L)).thenThrow(new EmptyResultDataAccessException(0));
		
		//act
		Card cardReturned = controller.delete(1L);
		
		//assert
		assertThat(cardReturned).isNull();
		verify(cardRepo).findOne(1L);
	}

}
