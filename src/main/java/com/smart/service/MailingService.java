package com.smart.service;


public interface MailingService {
	
	public boolean sendTextMail( String sendMailTo,String subject,String msg);
	public void sendAttachmentMail();
}
