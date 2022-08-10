package com.groupware.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReplyVO {

	private Long RNO; //댓글번호
	
	@JsonProperty("NOT_NO") 
	//언더바로 인해 파싱이 안돼서 문제발생 오라클 111에러. 
	//해결방법은 컬럼명인것같다. 어노테이션 추가해주기 ! 
	//json의 kety와 vo에 선언된 변수명이 달라 변환시 key이름을 변경하여 파싱하기위함(ajax)
	//{"NOT_NO":"NOT_NO"}
	private Long NOT_NO; //게시글번호(공지사항)
	
	private String REPLY; //댓글내용
	private String REPLYER; //댓글작성자
	private Date REPLYDATE; //댓글작성날짜
	private Date UPDATEDATE; //댓글수정날짜
	
}
