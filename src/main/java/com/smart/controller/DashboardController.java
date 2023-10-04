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
@Controller
@RequestMapping("dashboard")
public class DashboardController {
	
	@Autowired
	private ContactRepo contactRepo;

	@Autowired
	private UserRepository userRepo;

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

		try {
			User user = userRepo.getUserByUserName(principal.getName());

			Pageable pageable = PageRequest.of(page, 10);

			Page<Contact> contacts = contactRepo.getContactsByUser(user , pageable);
			model.addAttribute("currentPage",page);
			model.addAttribute("pageSize",contacts.getTotalPages());
			model.addAttribute("Contacts" , contacts);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		model.addAttribute("ContactPage", "activeNav");

		return "user/contacts";
	}



	@RequestMapping("/addcontacts")
	public String addContacts(Model model , Principal principal) {
		addUser(model,principal);
		model.addAttribute("AddContactPage", "activeNav");

		return "user/addContacts";
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
		model.addAttribute("AddContactPage", "activeNav");
		model.addAttribute("currentPage",0);
		try {
			if(mfile.isEmpty())
			contact.setImage("prof-pic.png");
			else
			{
			File saveFile = new ClassPathResource("/static/img").getFile();
			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator +mfile.getOriginalFilename());
			Files.copy(mfile.getInputStream(),  path ,StandardCopyOption.REPLACE_EXISTING);
			contact.setImage(contact.getcId()+mfile.getOriginalFilename());
			}
			contact.setDescription(description);


			User currentUser = userRepo.getUserByUserName(principal.getName());
			contact.setUser(currentUser);
			contactRepo.save(contact);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/user/dashboard/contacts";
	}

	@RequestMapping("/contact/update")
	public String updateContact(@RequestParam("id") Integer id  , Model model , Principal principal) {
		addUser(model,principal);
		model.addAttribute("updateMyprofile", "activeNav");
		Contact contact = contactRepo.getContactById(id);
		model.addAttribute("contactC",contact);
		return "user/updateContact";
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
		model.addAttribute("updateMyprofile", "activeNav");

		try {
			if(mfile.isEmpty())
			contact.setImage("prof-pic.png");
			else
			{
			File saveFile = new ClassPathResource("/static/img").getFile();
			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator +mfile.getOriginalFilename());
			Files.copy(mfile.getInputStream(),  path ,StandardCopyOption.REPLACE_EXISTING);
			contact.setImage(contact.getcId()+mfile.getOriginalFilename());
			}

			User currentUser = userRepo.getUserByUserName(principal.getName());

			Contact DBCon = contactRepo.getContactById(id);

			contact.setUser(currentUser);
			DBCon.setDescription(description);
			DBCon.setEmail(contact.getEmail());
			DBCon.setName(contact.getName());
			DBCon.setNickName(contact.getNickName());
			DBCon.setPhone(contact.getPhone());
			DBCon.setWork(contact.getWork());
			contactRepo.save(DBCon);
			model.addAttribute("contactC",DBCon);


		} catch (Exception e) {
			e.printStackTrace();
		}

		return "user/updateContact";
	}
}