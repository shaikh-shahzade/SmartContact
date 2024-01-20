package com.smart.service;

import java.io.File;
import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
public class MailingServiceImpl implements MailingService{
	
	private Properties properties;
	private String host;
	
	public MailingServiceImpl() {

		this.host = "smtp.gmail.com";

		this.properties = System.getProperties();
		properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        properties.put("mail.smtp.ssl.trust", host);
	}

	@Override
	public boolean sendTextMail( String sendMailTo , String subject , String msg  ) {
		
		Session session = Session.getInstance(this.properties, new Authenticator() {
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
			message.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(sendMailTo));
			message.setSubject(subject);
			message.setText(msg);


			Transport.send(message);
			return true;

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		return false;
	}

	@Override
	public void sendAttachmentMail() {
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
			message.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress("shahzade10@gmail.com"));
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
