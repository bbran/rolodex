package com.libertymutual.goforcode.rolodex.models;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.meanbean.test.BeanTester;

public class AddressTests {

	@Test
	public void test_Address() {
		BeanTester beanTester = new BeanTester();	
		beanTester.testBean(Address.class);
	}
	
	@Test
	public void test_constructor()	{
		//arrange
		Card card = new Card();
		
		//act
		Address address = new Address("Home", "123 Fake St.", "Fakesville", "DE", 12345, card);
		
		//assert
		assertThat(address.getType()).isEqualTo("Home");
		assertThat(address.getStreet()).isEqualTo("123 Fake St.");
		assertThat(address.getCity()).isEqualTo("Fakesville");
		assertThat(address.getState()).isEqualTo("DE");
		assertThat(address.getZipCode()).isEqualTo(12345);
		assertThat(address.getCard()).isSameAs(card);
	}

}
