package com.groupware.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MailServiceImpl implements MailService{
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Override
	public boolean send(String subject, String text, String from, String to) {
		log.info("send에 들어옴");
		
		MimeMessage message = javaMailSender.createMimeMessage();
		log.info("message의 값 : " + message);

		try {
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setSubject(subject);
			helper.setText(text, true);
			helper.setFrom(from);
			helper.setTo(to);

			javaMailSender.send(message);
			return true;

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}

}
