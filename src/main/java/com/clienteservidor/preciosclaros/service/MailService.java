package com.clienteservidor.preciosclaros.service;

import javax.mail.MessagingException;

public interface MailService {
	
	public void send(String toAddress, String fromAddress, String subject, String text);

}
