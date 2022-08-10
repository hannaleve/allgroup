package com.groupware.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MemberVO { //회원
	
	@JsonProperty("USER_ID") 
	private String USER_ID; //회원아이디
	@JsonProperty("USER_SNO") 
	private String USER_SNO; //사번
	@JsonProperty("USER_PWD") 
	private String USER_PWD; //회원비밀번호
	@JsonProperty("USER_NAME") 
	private String USER_NAME; //이름
	@JsonProperty("USER_RANK") 
	private String USER_RANK; //직급
	@JsonProperty("USER_EMAIL") 
	private String USER_EMAIL; //이메일
	@JsonProperty("USER_PHONE") 
	private String USER_PHONE; //휴대전화
	@JsonProperty("USER_ADDRES1") 
	private String USER_ADDRES1; //주소
	@JsonProperty("USER_ADDRES2") 
	private String USER_ADDRES2; //주소
	@JsonProperty("USER_ADDRES3") 
	private String USER_ADDRES3; //주소
	@JsonProperty("DEPT_NAME") 
	private String DEPT_NAME; //부서명
	private Date REGDATE; //등록날짜
	private Date UPDATEDATE; //수정날짜
	private boolean ENABLED; //활성화여부
	
	private List<AuthVO> authList; //권한구조

}
