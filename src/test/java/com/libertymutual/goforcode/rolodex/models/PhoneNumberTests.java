package com.libertymutual.goforcode.rolodex.models;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.meanbean.test.BeanTester;

public class PhoneNumberTests {

	@Test
	public void test_PhoneNumber() {
		BeanTester beanTester = new BeanTester();	
		beanTester.testBean(PhoneNumber.class);
	}
	
	@Test
	public void test_constructor()	{
		//arrange
		Card card = new Card();
		
		//act
		PhoneNumber phoneNumber = new PhoneNumber("Home", "1234567", card);
		
		//assert
		assertThat(phoneNumber.getType()).isEqualTo("Home");
		assertThat(phoneNumber.getPhoneNumber()).isEqualTo("1234567");
		assertThat(phoneNumber.getCard()).isSameAs(card);
	}

}
