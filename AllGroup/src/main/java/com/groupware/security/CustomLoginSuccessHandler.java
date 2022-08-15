package com.groupware.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {//로그인 성공 이후에 특정한 동작을 제어하고 싶을 때
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		log.warn("Login Success");
		
		List<String> roleNames = new ArrayList<>();
		
		//사용자가 가진 모든 권한을 문자열로 체크
		authentication.getAuthorities().forEach(authority -> {roleNames.add(authority.getAuthority());
		});
		
		log.warn("ROLE NAMES : " + roleNames); //어떤권한인지 나옴 ROLE_USER, ROLE_MEMBER, ROLE_ADMIN
		
		if(roleNames.contains("ROLE_ADMIN")) { //관리자 권한 이라면
			response.sendRedirect("/groupware/admin"); //로그인 후 바로 관리자 페이지
			return;
		}
		
		
		if(roleNames.contains("ROLE_MEMBER")) { //회원 권한 (일반 사용자)라면 - 로그인하지 않은 사용자는 취급 x login페이지로
			response.sendRedirect("/groupware/main"); //로그인 후 메인화면으로 이동
			return;
		}
		
		
		response.sendRedirect("/");
		
	}
	
	

	
	
	
	
}
