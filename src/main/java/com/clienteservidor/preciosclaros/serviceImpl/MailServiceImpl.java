package com.clienteservidor.preciosclaros.serviceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.clienteservidor.preciosclaros.dao.UserDao;
import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.service.MailService;

@Service("mailService")
public class MailServiceImpl implements MailService{
	
	@Autowired
	private MailSender mailSender;

	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
	@Autowired
	private UserDao userDao;
 
	public void send(String toAddress, String fromAddress, String subject, String text) { 
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(fromAddress);
		mail.setTo(toAddress);
		mail.setSubject(subject);
		mail.setText(text);	

		mailSender.send(mail);
	}
/*
	@Override
	public void sendHTML(String toAddress, String fromAddress, String subject, String html) throws MessagingException, MailException {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
		message.setContent(html, "text/html");
		helper.setTo(toAddress);
		helper.setSubject(subject);
		helper.setFrom(fromAddress);
		javaMailSender.send(message);
	}
	*/

}
