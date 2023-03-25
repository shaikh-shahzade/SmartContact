package com.smart.controller;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.repo.ContactRepo;
import com.smart.repo.UserRepository;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import jakarta.mail.internet.MimeMultipart;

@RestController
public class RestUtility {

	@Autowired
	ContactRepo contactRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/contacts/search")
	public List<Contact> search(@RequestParam("searchText") String searchText 
			, Principal principal)
	{
		User user = userRepo.getUserByUserName(principal.getName());
		return contactRepo.findTop5ByNameContainingAndUser(searchText , user);
	}
	
	@GetMapping("/mail")
	public String sendMail()
	{
		//These are for learning purpose only. Due to privacy not showing these.
		//sendTextMail();
		//sendAttachmentMail();		
		return "done";
	}

	private void sendTextMail() {
		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        
        properties.put("mail.smtp.ssl.trust", host);
		Session session = Session.getInstance(properties, new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			// TODO Auto-generated method stub
			return new  PasswordAuthentication("myMail@gmail.com","password");
		}
		});
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("myMail@gmail.com"));
			message.addRecipient(RecipientType.TO, new InternetAddress("sentTOMail@gmail.com"));
			message.setSubject("TEST");
			message.setText("This is mail send demo");
			
			
			Transport.send(message);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void sendAttachmentMail() {
		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        
        properties.put("mail.smtp.ssl.trust", host);
		Session session = Session.getInstance(properties, new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			// TODO Auto-generated method stub
			return new  PasswordAuthentication("shahzade10@gmail.com","cwuwffbrqggwgzdv");
		}
		});
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("shahzade10@gmail.com"));
			message.addRecipient(RecipientType.TO, new InternetAddress("shahzade10@gmail.com"));
			message.setSubject("TEST");
			
			MimeMultipart mimeMultipart = new MimeMultipart();
			
			String path = "C:\\Users\\shahz\\Downloads\\257821.jpg";
			
			MimeBodyPart textMime = new MimeBodyPart();
			textMime.setText("This is test to send attachment file on Email");
			
			MimeBodyPart fileMime = new MimeBodyPart();
			
			File file = new File(path);
			fileMime.attachFile(file);
			
			
			
			mimeMultipart.addBodyPart(textMime);
			mimeMultipart.addBodyPart(fileMime);

			message.setContent(mimeMultipart);
			Transport.send(message);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
