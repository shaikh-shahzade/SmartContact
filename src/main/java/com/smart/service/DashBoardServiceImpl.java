package com.smart.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Mail;
import com.smart.repo.ContactRepo;
import com.smart.repo.UserRepository;

@Service
public class DashBoardServiceImpl implements DashboardService {
	@Autowired
	private ContactRepo contactRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MailingService mailingService;

	@Override
	public String viewContacts(Model model, Principal principal, int page) {
		// TODO Auto-generated method stub

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

	@Override
	public String addContacts(Model model, Principal principal) {
		// TODO Auto-generated method stub
		model.addAttribute("AddContactPage", "activeNav");
		return "user/addContacts";
	}

	@Override
	public String addContact(Contact contact, Model model, Principal principal, MultipartFile mfile,
			String description) {
		// TODO Auto-generated method stub
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

		return "redirect:/dashboard/contacts";
	}

	@Override
	public String updateContact(Integer id, Model model, Principal principal) {
		// TODO Auto-generated method stub

		model.addAttribute("updateMyprofile", "activeNav");
		Contact contact = contactRepo.getContactById(id);
		model.addAttribute("contactC",contact);
		return "user/updateContact";
	}

	@Override
	public String updateValueContact(Contact contact, Model model, Principal principal, MultipartFile mfile, Integer id,
			String description) {
		// TODO Auto-generated method stub

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

	@Override
	public String mail(Model model, Principal principal) {
		model.addAttribute("mailPage", "activeNav");
		model.addAttribute("mail",new Mail());
		return "user/mail";
	}

	@Override
	public String mailPost(Model model, Principal principal, Mail mail, String mailBody,List<Integer> mailIds) {
		
		
		 List<String> mailId = mailIds.stream().map(id ->contactRepo.getContactById(id).getEmail()).toList();
		 
		System.out.println(mail.toString());
		//boolean success = mailingService.sendTextMail(mail.getTo(), mail.getSubject(), mailBody,  mailId);
		return "user/mail";
	}

}
