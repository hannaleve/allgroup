package com.groupware.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data //get,set,toString()
@Getter
@Setter
public class NoticeVO { //공지사항 
	
	private Long NOT_NO; //게시물번호
	private String USER_NAME; //작성자(회원)
	private String NOT_TITLE; //제목
	private String NOT_CONTENT; //내용
	private int NOT_COUNT; //조회수
	private Date NOT_REGDATE; //작성날짜
	private Date NOT_UPDATE; //수정날짜
	private Long USER_NO; //회원번호
	private int REPLYCNT; //댓글수


	
}
