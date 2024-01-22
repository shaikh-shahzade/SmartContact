package com.smart.user.config;

import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class MailPropertiesConfig implements InitializingBean{

	private Properties properties;
	@Value("${mail-config.config.host}")
	private String host;
	@Value("${mail-config.config.username}")
	private String username;
	@Value("${mail-config.config.password}")
	private String password;
	@Value("${mail-config.config.port}")
    private String port;
	@Value("${mail-config.config.ssl-enable}")
    private Boolean sslEnable;
	@Value("${mail-config.config.auth}")
    private Boolean auth;
	@Value("${mail-config.config.starttls-enable}")
    private Boolean starttlsEnable;
	@Value("${mail-config.config.ssl-trust}")
    private Boolean sslTrust;
	@Value("${mail-config.config.sender-mail}")
	private String senderMail;
	
	public Properties getProperties() {
		return properties;
	}

	public String getUsername() {
		return username;
	}

	

	public String getPassword() {
		return password;
	}

	

	public String getSenderMail() {
		return senderMail;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.print("Value:"+ host);
		this.properties = System.getProperties();
		this.properties.put("mail.smtp.host", this.host);
		this.properties.put("mail.smtp.port", this.port);
		this.properties.put("mail.smtp.ssl.enable", this.sslEnable);
		this.properties.put("mail.smtp.auth", this.auth);
		this.properties.put("mail.smtp.starttls.enable", this.starttlsEnable);
		this.properties.put("mail.smtp.ssl.trust", this.sslTrust);
	}

	
	

	
}
