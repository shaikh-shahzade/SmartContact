package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.repo.ContactRepo;
import com.smart.repo.UserRepository;
import com.smart.service.DashboardService;
@Controller
@RequestMapping("dashboard")
public class DashboardController {
	
	@Autowired
	private ContactRepo contactRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private DashboardService dashboardService;

	private void addUser(Model model , Principal principal)
	{
		if(principal!=null)
		{
			try {
				User user = userRepo.getUserByUserName(principal.getName());
				model.addAttribute("username",user.getName());
				model.addAttribute("login",true);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	@RequestMapping("/contacts")
	public String viewContacts(
			Model model ,
			Principal principal,
			@RequestParam(name = "page" , defaultValue = "0")  int page
			) {
		addUser(model,principal);
		return dashboardService.viewContacts(model, principal, page);
		
	}



	@RequestMapping("/addcontacts")
	public String addContacts(Model model , Principal principal) {
		addUser(model,principal);
		return dashboardService.addContacts(model, principal);
	}

	@PostMapping("/addcontacts")
	public String addContact(
			@ModelAttribute Contact contact ,
			Model model ,
			Principal principal ,
			@RequestParam("img") MultipartFile mfile ,
			@RequestParam("description") String description
			) {

		addUser(model,principal);
		return dashboardService.addContact(contact, model, principal, mfile, description);
	}

	@RequestMapping("/contact/update")
	public String updateContact(@RequestParam("id") Integer id  , Model model , Principal principal) {
		addUser(model,principal);
		return dashboardService.updateContact(id, model, principal);
	}


	@RequestMapping(value = "/contact/update" , method = RequestMethod.POST)
	public String updateValueContact(
			@ModelAttribute Contact contact ,
			Model model ,
			Principal principal ,
			@RequestParam("img") MultipartFile mfile ,
			@RequestParam("id")Integer id,
			@RequestParam("description") String description

			) {
		addUser(model,principal);
		return dashboardService.updateValueContact(contact, model, principal, mfile, id, description);
	}
}
