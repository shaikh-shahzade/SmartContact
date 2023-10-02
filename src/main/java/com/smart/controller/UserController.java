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
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ContactRepo contactRepo;


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

	@RequestMapping("/dashboard/myprofile")
	public String myProfile(Model model , Principal principal) {
		addUser(model,principal);
		model.addAttribute("myProfilePage", "activeNav");

		return "user/myProfile";
	}
	@RequestMapping("/dashboard/profile/{id}")
	public String profile(@PathVariable("id") Integer contactID , Model model , Principal principal) {
		addUser(model,principal);
		model.addAttribute("myProfilePage", "activeNav");
		try {
			Contact contact = contactRepo.getContactById(contactID);
			model.addAttribute("contactProfile", contact);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "user/Profile";
	}
	@RequestMapping("/dashboard/update/myprofile")
	public String updateProfile(Model model , Principal principal) {
		addUser(model,principal);
		model.addAttribute("updateMyprofile", "activeNav");

		return "user/updateProfile";
	}

	
}
