package com.smart.service;

import java.security.Principal;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.helper.Mail;

 
public interface DashboardService {

	public String viewContacts(Model model, Principal principal, int page);

	public String addContacts(Model model, Principal principal);

	public String addContact(Contact contact, Model model, Principal principal, MultipartFile mfile,
			String description);

	public String updateContact(Integer id, Model model, Principal principal);

	public String updateValueContact(Contact contact, Model model, Principal principal, MultipartFile mfile, Integer id,
			String description);

	public String mail(Model model, Principal principal);

	public String mailPost(Model model, Principal principal, Mail mail, String mailBody, List<Integer> mailIds, MultipartFile attachment);
}
