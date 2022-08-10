package com.groupware.exception;

import org.springframework.http.HttpStatus;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

@ControllerAdvice //예외를 처리하는 존재 명시
@Log4j
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class) //예외타입 명시 
	public String except(Exception ex, Model model) {
		log.error("Exception............" + ex.getMessage());
		
		model.addAttribute("exception",ex);
		log.error(model);
		return "error_page";
	}

	@ExceptionHandler(NoHandlerFoundException.class) //예외타입 명시
	@ResponseStatus(HttpStatus.FOUND)
	public String except404(NoHandlerFoundException ex, Model model) {
		return "custom404";
	}
}
