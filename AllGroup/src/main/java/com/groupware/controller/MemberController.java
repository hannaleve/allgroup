package com.groupware.controller;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.groupware.service.MailService;
import com.groupware.service.MemberService;
import com.groupware.vo.MemberVO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/member/*") 
//@AllArgsConstructor
@PropertySource(value = {"classpath:/application.properties"})
public class MemberController {

	@Setter(onMethod=@__({@Autowired}))
	private MemberService memberservice;

	@Setter(onMethod=@__({@Autowired}))
	private MailService mail;

	@Setter(onMethod=@__({@Autowired}))
	private PasswordEncoder encoder;

	
	 @Value("${mail.username}") String send;
	 

	@GetMapping("/joinForm")
	public void insertMember() { //회원가입

	}

	@RequestMapping(value="/member/joinForm", method=RequestMethod.POST)
	public String postRegister(MemberVO memberVO) { //회원가입전송 시
		log.info("회원가입하기...!!!");
		log.info(memberVO);

		//패스워드 암호화
		String enPass = encoder.encode(memberVO.getUSER_PWD()); 
		memberVO.setUSER_PWD(enPass);

		//부서명에 해당하는 부서코드 알아보기
		String depCod = memberservice.deptCheck(memberVO.getDEPT_NAME());
		log.info("부서코드잘나옴? : " + depCod);

		Calendar cal = Calendar.getInstance();
		Random r = new Random();

		int year = cal.get (cal.YEAR);//년도만 추출

		int i = r.nextInt(300)+1; //1~300까지 난수

		//사번추출해서 저장
		String sno = year + "_" + depCod + i; //현재년도+_+부서코드명+랜덤값

		memberVO.setUSER_SNO(sno); //사번저장

		memberservice.insert(memberVO); //회원가입등록완료

		String userid = memberVO.getUSER_ID();

		memberservice.insertRole(userid); //해당 회원 권한등록


		return "redirect:/customLogin"; //로그인페이지로.
	}


	@GetMapping("/idCheck")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public int idCheck(@RequestParam("userId")String user_id) { //아이디 중복체크 
		return memberservice.userIdCheck(user_id);
	}

	@GetMapping("/emailCheck")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public int emailCheck(@RequestParam("user_email")String user_email) { //이메일 중복체크 
		return memberservice.userEmailCheck(user_email);
	}

	public void setMailService(MailService mailService) {
		this.mail = mailService;
	}

	
	

	@PostMapping("/email")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String, String> sendMail(@RequestBody Map<String, String> map, HttpSession session) { //이메일인증
		log.info("/checkEmailAjax에 들어옴");
		System.out.println("입력받은 email의 값 : " + map.get("email")); //ajax로 보낸 값 

		int random = new Random().nextInt(100000) + 10000; // 10000 ~ 99999
		log.info("random의 값 : " + random);

		String joinCode = String.valueOf(random);
		log.info("joinCode의 값 : " + joinCode);

		session.setAttribute("joinCode", joinCode);

		String subject = "회원가입 인증 코드 입니다.";
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("안녕하세요. 'ALL-GROUP'입니다.\r귀하의 인증 코드는  <" + joinCode + "> 입니다.");
		log.info(stringBuilder.toString());
		log.info(send);


		boolean finishSend = mail.send(subject, stringBuilder.toString(), send, map.get("email"));
		log.info("성공이냐 실패냐 : " + finishSend);

		map.put("joinCode", joinCode);

		log.info(map);

		return map;
	}








}
