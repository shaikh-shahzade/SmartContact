package com.smart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smart.entities.Contact;
import com.smart.repo.ContactRepo;

@RestController
public class SearchController {

	@Autowired
	ContactRepo contactRepo;
	
	@GetMapping("/contacts/search")
	public List<Contact> search(@RequestParam("searchText") String searchText)
	{
		return contactRepo.findByNameContaining(searchText);
	}
}
