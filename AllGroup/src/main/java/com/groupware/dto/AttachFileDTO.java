package com.groupware.dto;

import lombok.Data;

@Data
public class AttachFileDTO { //첨부파일 정보 저장하는 DTO
	
	private String fileName; //원본파일이름
	private String uploadPath; //업로드경로
	private String uuid; //uuid랜덤값
	private boolean image; //이미지여부
	

}
