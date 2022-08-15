package com.groupware.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.groupware.mapper.MemberMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
@AllArgsConstructor
@Service
public class MailServiceImpl implements MailService{

	@Autowired
	JavaMailSender javaMailSender;


	@Override 
	public boolean send(String subject, String text, String from,String to) { 
		log.info("send에 들어옴");

		MimeMessage message = javaMailSender.createMimeMessage();
		log.info("message의 값 : " + message);

		try {

			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setSubject(subject); helper.setText(text, true); helper.setFrom(new
					InternetAddress(from)); helper.setTo(new InternetAddress(to));

					javaMailSender.send(message); return true;

		} catch (MessagingException e) { e.printStackTrace(); } return false; }

}
