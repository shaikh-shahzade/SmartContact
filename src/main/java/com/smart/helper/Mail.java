package com.smart.helper;

import org.springframework.web.multipart.MultipartFile;

public class Mail {
	private String to;
	private String cc;
	private String subject;
	private String body;
	private MultipartFile attachment;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public MultipartFile getAttachment() {
		return attachment;
	}
	public void setAttachment(MultipartFile attachment) {
		this.attachment = attachment;
	}
	public Mail(String to, String cc, String subject, String body, MultipartFile attachment) {
		super();
		this.to = to;
		this.cc = cc;
		this.subject = subject;
		this.body = body;
		this.attachment = attachment;
	}
	public Mail() {
		super();
		// TODO Auto-generated constructor stub
	}
}
