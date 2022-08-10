package com.groupware.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PhotoVO { //공지사항 에디터 vo
	
	//photo_upload.html페이지에 form태그 내 존재하는 file태그의 name명 일치
	private MultipartFile Filedata;
	private String callback; //callback URL
	private String callback_func;
	
	

}
