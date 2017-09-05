package com.libertymutual.goforcode.rolodex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.rolodex.models.PhoneNumber;

@Repository
public interface PhoneNumberRepo extends JpaRepository<PhoneNumber, Long> {

}
