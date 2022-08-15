package com.groupware.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.groupware.common.Media;
import com.groupware.common.UploadFile;
import com.groupware.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@PropertySource(value = {"classpath:/application.properties"})
public class SmartEditorController {
	@Value("${file.path}")
	private String uploadPath;
	
	
	//naver smarteditor위한 컨트롤러
   //resource - smartedtirot - sample - photo_uploader - attach_photo.js에서 html5Upload함수내에 업로드 경로 맞추기 /uploadEditorForm
	
	@ResponseBody
	@RequestMapping(value="/uploadEditorForm", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadForm( //에디터업로드폼태그 
			
			//헤더정보 직접 추출하고, request 객체 이용하여 파일데이터 추출하고 보관함.
			//에디터의 업로드 경우 데이터는 문자열로 받은 후 js에서 json형태로 변환 후 이를 수정하는 방법을 사용할것임.
			
			@RequestHeader("contentType") String contentType,
			@RequestHeader("file-name") String fileName,
			@RequestHeader("file-size") long fileSize,
			@RequestHeader("file-Type") String fileType,
			HttpServletRequest request) throws Exception {
		fileName = URLDecoder.decode(fileName,"UTF-8");
		
		log.info("--------에디터 파일 폼-------------");
		log.info(contentType);
		log.info(fileName);
		log.info(" " + fileSize);
		log.info(fileType);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		 
		IOUtils.copy(request.getInputStream(),bos);
		
		log.info("LENGTH : " + bos.size());
		
		String uploadedName = UploadFile.uploadEditorFile(uploadPath, fileName, bos.toByteArray()); //에디터 업로드 
		
		log.info("에디터 업로드 UPLOADED NAME: " + uploadedName); // /2022/03/20/s_c51f3d36-5414-47c3-93bd-c67931e3da8c_다운로드.png
		
		uploadedName = uploadedName.replaceAll("/", ":"); 

		log.info("에디터업로드 업로드 이름 : " + uploadedName); // 2022:03:20:s_c51f3d36-5414-47c3-93bd-c67931e3da8c_다운로드.png
		
		String url = "callback=javascript:cb";
		url += "&bNewLine=true";
		url += "&sFileName=" + fileName;
		url += "&sFileURL=/displayEditorFile/" + uploadedName; // 경로 + :2022:03:20:s_81.....

		ResponseEntity<String> result = new ResponseEntity<>(url, HttpStatus.OK);
		return result;
		
		
	}
	@ResponseBody
	@RequestMapping("/displayEditorFile/{fileName:.+}")
	public ResponseEntity<byte[]> displayFile(@PathVariable("fileName") String fileName) throws Exception { //이미지전송(저장)버튼 클릭 시 

		log.info("이미지 전송------------displayFile");
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		
		fileName = fileName.replaceAll(":", "/");

		log.info("display FILE NAME: " + fileName); // /2022/03/20/s_c51f3d36-5414-47c3-93bd-c67931e3da8c_다운로드.png

		
		
		try {

			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1); //png
			
			log.info("displayFile FORMAT NAME: " + formatName);

			MediaType mType = Media.getMediaType(formatName);

			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(uploadPath +File.separator+ fileName);

			if (mType != null) {
				headers.setContentType(mType);
			} else {

				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition",
						"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			}

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	
	
	//에디터상에서 업로드된 파일 삭제 하기 위해
	//resource - smarteditor - sample - js - plugin - hp_SE2M..... - $ON_SET_PHOTO 함수 중
	//callback: htData.callback || "" 콜백처리 추가
	
	 @ResponseBody
	  @RequestMapping(value="/deleteEditorFile", method=RequestMethod.POST)
	  public ResponseEntity<String> deleteFile(String fileName){ //파일삭제 
	    
	    log.info("delete Editor file: "+ fileName);
	    
	    fileName = fileName.replace(":", "/");
	    
	    String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
	    
	    MediaType mType = Media.getMediaType(formatName);
	    
	    if(mType != null){      
	      
	      String front = fileName.substring(0,12);
	      String end = fileName.substring(14);
	      new File(uploadPath + (front+end).replace('/', File.separatorChar)).delete();
	    }
	    
	    new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
	    
	    
	    return new ResponseEntity<String>("deleted", HttpStatus.OK);
	  }  
}
	

