package com.clienteservidor.preciosclaros.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Bean
	public JavaMailSenderImpl mailSender() {
	    JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
	    javaMailSenderImpl.setHost("smtp.gmail.com");
	    javaMailSenderImpl.setPort(587);
	    javaMailSenderImpl.setUsername("preciosclaros.servehttp.com@gmail.com");
	    javaMailSenderImpl.setPassword("11235qubits");
	    javaMailSenderImpl.setJavaMailProperties(javaMailProperties());
	    return javaMailSenderImpl;
	}

	public Properties javaMailProperties(){
	    Properties properties = new Properties();

	    properties.put("mail.transport.protocol", "smtp");
	    properties.put("mail.smtp.auth", true);
	    properties.put("mail.smtp.starttls.enable", true);
	    properties.put("mail.debug", true);
	    return properties;
	}
	
}
