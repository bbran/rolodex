package com.libertymutual.goforcode.rolodex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.rolodex.models.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

}
