package com.groupware.controller;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.groupware.service.MailService;
import com.groupware.service.MemberService;
import com.groupware.service.ReplyService;
import com.groupware.vo.MemberVO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
public class LoginController {

	private MemberService memberservice;
	
	@Setter(onMethod=@__({@Autowired}))
	private PasswordEncoder encoder;


	@GetMapping("/accessError")
	public void acceccDenied(Authentication authentication, Model m) { 
		log.info("access Denied : " + authentication);

		m.addAttribute("msg","Access Denied");
	}


	@GetMapping("/customLogin")
	public void customLoing(String error, String logout, Model m) { //로그인페이지
		log.info("error : " + error);
		log.info("logout : " + logout);

		if(error != null) {
			m.addAttribute("error","Login Error Check Your Account");
		}

		if(logout != null) {
			m.addAttribute("logout", "logout ! bye ~ ");
		}

	}

	@PostMapping("/login")
	public String userLogin(MemberVO v, Model m) { //customLogin로그인페이지에서 로그인 후 post로 해당 아이디,비번정보 넘겼을 시 들어오는 컨트롤러
		
		MemberVO v1 = memberservice.loginUser(v); //로그인한사용자가 db등록된 사용자인지 체크

		if(v1 != null && encoder.matches(v.getUSER_PWD(),v1.getUSER_PWD())) { //입력한 패스워드와 복호화된 패스워드 비교
			return "redirect:/groupware/main"; //메인페이지로 이동! ROLE_MEMBER (회원가입한 회원인경우 들어갈 수 있는 페이지)
		}else {
			return "redirect:/customLogin"; //권한 없고 pwd가 다른경우 로그인페이지로
		}
	}

	@GetMapping("/customLogout")
	public String customLogout(RedirectAttributes attributes,HttpServletRequest request) { //로그아웃페이지
		log.info("logout....!!!");

		if(request.getSession() != null) {
			log.info("로그아웃실패");
		}else {
			log.info("로그아웃성공");
		}
		return "redirect:/customLogin";
	}
}
