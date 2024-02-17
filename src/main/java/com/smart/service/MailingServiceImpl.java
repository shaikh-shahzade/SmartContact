package com.smart.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.user.config.MailPropertiesConfig;

import jakarta.activation.DataHandler;
import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimePart;

@Service
public class MailingServiceImpl implements MailingService{
	@Autowired
	private MailPropertiesConfig mailPropertiesConfig;

	@Override
	public boolean sendTextMail( String sendMailTo , String subject , String msg , List<String> mailIds,MultipartFile attachment ) {
		
		Session session = Session.getInstance(mailPropertiesConfig.getProperties(), new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new  PasswordAuthentication(mailPropertiesConfig.getUsername(),mailPropertiesConfig.getPassword());
		}
		});
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(this.mailPropertiesConfig.getSenderMail()));
			
			List<InternetAddress> ia = new ArrayList<InternetAddress>();
			for(String mails: mailIds)
				ia.add(new InternetAddress(mails));
			
			message.addRecipients(jakarta.mail.Message.RecipientType.TO, 
					 
					ia.toArray(new InternetAddress[ia.size()])
					
			);
			
			message.setSubject(subject);
			//message.setText("message");
			if(attachment!=null)
			message.setContent
			(addAttachment(attachment));
			
			Transport.send(message);
			return true;

		} catch (MessagingException e) {
			e.printStackTrace();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	
	public Multipart addAttachment(MultipartFile attachment) throws IllegalStateException, IOException, MessagingException {
		
		
			Multipart mimeMultipart = new MimeMultipart();
			MimeBodyPart fileMime = new MimeBodyPart();
			
			//To save in directory
			String path = System.getProperty("user.dir")+"\\" + attachment.getOriginalFilename();
			File temp = new File(path);
			attachment.transferTo(temp);
			fileMime.attachFile(temp);

			mimeMultipart.addBodyPart(fileMime);

			return mimeMultipart;

		
	}

}
