package com.smart.service;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smart.entities.Contact;
import com.smart.user.config.MailPropertiesConfig;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
public class MailingServiceImpl implements MailingService{
	@Autowired
	private MailPropertiesConfig mailPropertiesConfig;

	@Override
	public boolean sendTextMail( String sendMailTo , String subject , String msg , List<String> mailIds ) {
		
		System.out.print(mailIds.toString() + " sendMail "+ subject+" subj   msg " + msg);
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
			message.setText("message");
			Transport.send(message);
			return true;

		} catch (MessagingException e) {
			e.printStackTrace();

		}
		
		return false;
	}

	@Override
	public void sendAttachmentMail() {
		
		Session session = Session.getInstance(mailPropertiesConfig.getProperties(), new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			// TODO Auto-generated method stub
			return new  PasswordAuthentication(mailPropertiesConfig.getUsername(),mailPropertiesConfig.getPassword());
		}
		});
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(mailPropertiesConfig.getSenderMail()));
			message.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(mailPropertiesConfig.getSenderMail()));
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
