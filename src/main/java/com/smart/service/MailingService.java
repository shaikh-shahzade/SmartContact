package com.smart.service;

import java.util.List;

public interface MailingService {
	
	public boolean sendTextMail( String sendMailTo,String subject,String msg, List<String> mailIds);
	public void sendAttachmentMail();
}
