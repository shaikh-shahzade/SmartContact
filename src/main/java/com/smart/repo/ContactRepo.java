package com.smart.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;
import com.smart.entities.User;

public interface ContactRepo extends JpaRepository<Contact, Integer>{

	@Query("SELECT c FROM Contact c WHERE c.cId =:Id")
	public Contact getContactById(@Param("Id") Integer contactId);


	@Query("SELECT c FROM Contact c WHERE c.user =:userC")
	public Page<Contact> getContactsByUser(@Param("userC") User userID , Pageable pageable);

	public List<Contact> findTop5ByNameContainingAndUser(String name , User user);

	public void deleteBycIdAndUser(Integer cId , User user);
}
