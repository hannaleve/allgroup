package com.groupware.service;

public interface MailService {

	/*
	---메일전송---
	
	subject : 제목
	text : 내용
	from : 보내는 메일 주소
	to : 받는 메일 주소
	
	 */
	public boolean send(String subject, String text, String from, String to);

}
