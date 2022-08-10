package com.groupware.mapper; 

import com.groupware.vo.MemberVO;

public interface MemberMapper {

	public MemberVO read(String userid);
	
	public int checkId(String userid);
	
	public int emailCheck(String user_email);
	
	public void insert(MemberVO memberVO);
	
	public void insertRole(String userid);
	
	public MemberVO loginUser (MemberVO memberVO);
	
	public String deptChecks(String code);
}
