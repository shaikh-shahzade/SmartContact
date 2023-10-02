package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.repo.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepo;
	
	public void setAttributes(Model model , Principal principal)
	{
		if(principal!=null)
		{

			try {
				User user = userRepo.getUserByUserName(principal.getName());
				model.addAttribute("username", user.getName());
				model.addAttribute("login",true);
			} catch (Exception e) {

			}

		}
		else
			model.addAttribute("login",false);
	}

	@RequestMapping( value={"/home" , "/"})
	public String home(Model model , Principal principal) {
		setAttributes(model, principal);
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model,Principal principal) {
		setAttributes(model, principal);
		return "about";
	}
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			@RequestParam(value = "desc") String desc, Model model, HttpSession session) {

		if (agreement) {

			if (result.hasErrors()) {
				model.addAttribute("user", user);
				System.out.print("Error is here");
				return "signup";
			}
			user.setAbout(desc);
			user.setImage("default.png");
			user.setRole("user");

			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			try {
				User res = userRepo.save(user);
				model.addAttribute("user", res);
				session.removeAttribute("message");
			} catch (Exception e) {
				System.out.println("message");
				session.setAttribute("message", new Message("Email already exists", "alert-danger"));
			}

		} else
			session.setAttribute("message", new Message("Please accept the agreement.", "alert-warning"));

		return "signup";
	}

	@RequestMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("user", new User());
		return "login";
	}
	
	
}
