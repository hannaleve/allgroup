package com.groupware.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomAccessDeniedHandler  implements AccessDeniedHandler{
	
	// URL 접근 제한 되었을 경우 처리 부분 접근제한 메세지처리.(에러메세지)
	// URL접근권한없는 사용자가 들어왔을 경우 ex) 회원이 관리자페이지접근
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		 log.info("Access Denied Handler");
		 
		 log.info("Redirect!!");
		 
		 response.sendRedirect("/accessError");
		
	}

	
}
