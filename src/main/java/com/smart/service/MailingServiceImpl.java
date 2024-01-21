package com.smart.service;

import java.io.File;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
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
	@Value("${mail.properties.host}")
	private String host;
	@Value("${mail.properties.username}")
	private String username;
	@Value("${mail.properties.password}")
	private String password;
	@Value("${mail.properties.port}")
    private String port;
	@Value("${mail.properties.ssl-enable}")
    private Boolean sslEnable;
	@Value("${mail.properties.auth}")
    private Boolean auth;
	@Value("${mail.properties.starttls-enable}")
    private Boolean starttlsEnable;
	@Value("${mail.properties.ssl-trust}")
    private Boolean sslTrust;
	
	public MailingServiceImpl() {

		this.host = "smtp.gmail.com";

		this.properties = System.getProperties();
		this.properties.put("mail.smtp.host", this.host);
		this.properties.put("mail.smtp.port", this.port);
		this.properties.put("mail.smtp.ssl.enable", this.sslEnable);
		this.properties.put("mail.smtp.auth", this.auth);
		this.properties.put("mail.smtp.starttls.enable", this.starttlsEnable);
		this.properties.put("mail.smtp.ssl.trust", this.sslTrust);
	}

	@Override
	public boolean sendTextMail( String sendMailTo , String subject , String msg  ) {
		
		Session session = Session.getInstance(this.properties, new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new  PasswordAuthentication(username,password);
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
			e.printStackTrace();

		}
		
		return false;
	}

	@Override
	public void sendAttachmentMail() {
		
		Session session = Session.getInstance(this.properties, new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			// TODO Auto-generated method stub
			return new  PasswordAuthentication(username,password);
		}
		});
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("myMail@gmail.com"));
			message.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress("myMail@gmail.com"));
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
