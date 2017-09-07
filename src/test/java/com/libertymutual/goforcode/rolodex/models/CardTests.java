package com.libertymutual.goforcode.rolodex.models;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.meanbean.test.BeanTester;

public class CardTests {

	@Test
	public void test_Card() {
		BeanTester beanTester = new BeanTester();	
		beanTester.testBean(Card.class);
	}
	
	@Test
	public void test_constructor()	{
		//act
		Card card = new Card("Bob", "Jones", "Director", "Liberty Mutual", "https://www.libertymutual.com/");
		
		//assert
		assertThat(card.getFirstName()).isEqualTo("Bob");
		assertThat(card.getLastName()).isEqualTo("Jones");
		assertThat(card.getTitle()).isEqualTo("Director");
		assertThat(card.getCompanyName()).isEqualTo("Liberty Mutual");
	}

}
