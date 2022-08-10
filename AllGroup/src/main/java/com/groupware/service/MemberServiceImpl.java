package com.groupware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.mapper.MemberMapper;
import com.groupware.vo.MemberVO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service 
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{

	@Setter(onMethod=@__({@Autowired}))
	private MemberMapper mapper;

	@Override
	public int userIdCheck(String user_id) {
		log.info("user idCheck....!!!!");
		return mapper.checkId(user_id);
	}

	@Override
	public int userEmailCheck(String user_email) {
		log.info("이메일중복");
		return mapper.emailCheck(user_email);
	}

	@Override
	public void insert(MemberVO memberVO) {
		log.info("회원가입중입니다..!!");

		mapper.insert(memberVO);
	}

	@Override
	public MemberVO loginUser(MemberVO memberVO) {
		log.info("로그인체크입니다!");

		return mapper.loginUser(memberVO);
	}

	@Override
	public String deptCheck(String code) {

		return mapper.deptChecks(code);
	}

	@Override
	public void insertRole(String userid) {
		mapper.insertRole(userid);
		
	}
}
