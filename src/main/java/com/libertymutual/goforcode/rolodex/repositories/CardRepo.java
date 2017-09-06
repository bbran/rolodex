package com.libertymutual.goforcode.rolodex.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.rolodex.models.Card;

@Repository
public interface CardRepo extends JpaRepository<Card, Long> {
	
	List<Card> findByLastName(String lastName);
		
}
