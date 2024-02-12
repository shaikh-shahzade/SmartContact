package com.smart.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.internet.MimePart;

public interface MailingService {
	
	public boolean sendTextMail( String sendMailTo,String subject,String msg, List<String> mailIds, MultipartFile attachment);
	
}
