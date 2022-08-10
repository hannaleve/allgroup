package com.groupware.service;

import com.groupware.vo.MemberVO;

public interface MemberService {

	public int userIdCheck(String user_id); //아이디 중복체크 
	
	public int userEmailCheck(String user_email); //이메일 중복체크
	
	public void insert(MemberVO memberVO); //회원가입
	
	public MemberVO loginUser(MemberVO memberVO); //로그인시 체크
	
	public String deptCheck(String code); //부서확인
	
	public void insertRole(String userid); //회원권한등록
}
