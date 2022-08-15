package com.groupware.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;


import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.groupware.dto.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
@PropertySource(value = {"classpath:/application.properties"})
public class UploadController { //첨부파일 (엑셀/회원가입 프로필사진 시 테이블 생성 후 사용하기)
	
	@Value("${file.path}")
	private String uploadFolder;
	
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	@PostMapping(value = "/uploadAjaxAction",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) { //첨부파일등록
		log.info("upload ajax post!");
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolderPath = getFolder();

		
		
		//파일 생성
		File uploadPath = new File(uploadFolder,uploadFolderPath); //년/월/일로 파일 생성
		log.info("upload path : "  + uploadPath);
		
		if(uploadPath.exists() == false) { //파일 없다면
			uploadPath.mkdir(); //생성
		}
		

		for(MultipartFile file : uploadFile) {
			log.info("-------");
			
			log.info("Upload File Name : " + file.getOriginalFilename());
			log.info("Upload File Size : " + file.getSize());
			
			AttachFileDTO attachFileDTO = new AttachFileDTO();
			String uploadFileName = file.getOriginalFilename();
			
			//IE인경우 전체 파일 경로가 전송되기때문에 마지막 \ 기준으로 잘라낸 문자열이 실제 파일 이름
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("only file name : " + uploadFileName);
			
			UUID uuid = UUID.randomUUID(); // 중복방지 위함
			uploadFileName = uuid.toString() + "_" + uploadFileName; //원래의 파일이름과 구분하기 위함 (중복된 파일이 생길경우 _언더바로 구분가능)
		
			
			try {
				File saveFile = new File(uploadPath,uploadFileName);
				file.transferTo(saveFile); //파일저장 
				
				attachFileDTO.setUuid(uuid.toString()); //랜덤값 저장
				attachFileDTO.setUploadPath(uploadFolderPath); //업로드경로저장
				
				//만약 이미지 타입이라면 섬네일로 처리하도록 (원본파일 + 섬네일파일 s_로 시작)
				
				//일반파일은 그냥 파일 업로드만 가능
				if(checkImageType(saveFile)) {
					
					attachFileDTO.setImage(true); //이미지여부 true로 저장 
					
					FileOutputStream fileOutputStream = new FileOutputStream(new File(uploadPath,"s_" + uploadFileName));
					
					Thumbnailator.createThumbnail(file.getInputStream(),fileOutputStream,100,100); //사이지 가로,세로 100,100
					
					fileOutputStream.close();
				}
				
				list.add(attachFileDTO); //dto에 내용들 list에 담기 
				
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<List<AttachFileDTO>>(list,HttpStatus.OK);
	}
	
	
	
	//파일경로 포함된 파일이름 받다.
	//파일경로 + 's_' + uuid 붙은 파일이름 파라미터로 받기 
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) {
		log.info("fileName : " + fileName);
		
		File file = new File("c:\\upload\\"+fileName);
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			//byte[]로 전송할 때 MIME타입데이터를 헤더메세지에 포함하여 전송한다.(적절한 MIME타입 데이터 사용위함 - probeContentType)
			header.add("Content-Type", Files.probeContentType(file.toPath())); 
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	@GetMapping(value="/download",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName) {	//일반파일 다운로드(한글,엑셀,pdf..등)
		log.info("download file : "  + fileName);
		
		Resource resource = new FileSystemResource("c:\\upload\\"+fileName); //이거 다시 수정 필요.
		
		log.info("resource : " + resource);
		
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		
		//일반파일을 다운로드할 시엔 실제 파일이름으로 저장될 수 있도록 UUID값 이름을 지워주고 순수한 파일이름으로 저장함
		//UUID 부분을 잘라낸 상태의 이름으로 저장한다. -> 다운르드 시 순수한 파일이름으로 저장됨 확인.
		 String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);

		HttpHeaders headers = new HttpHeaders();
		
		try {
			String downloadName = null;
			
			downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
			
			log.info("일반파일 다운로드 이름 : " + downloadName);

			headers.add("Content-Disposition","attachment; filename=" + downloadName);
				
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
	
	}
	
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) { //첨부파일 삭제
		log.info("deleteFile : "  + fileName);
		
		File file;
		
		try {
			
			file = new File("C:\\upload\\"+URLDecoder.decode(fileName,"UTF-8"));
			
			file.delete(); //일반파일인 경우 파일만 삭제
			
			if(type.equals("image")) { //이미지인경우 섬네일도 존재함으로 일반파일로 만들어주기 "s_" -> "" 
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				
				log.info("largeFileName : " + largeFileName);
				
				file = new File(largeFileName);
				
				file.delete(); //원본이미지로 만들어서 파일 삭제 (s_없음)
			}
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
	
	
	
	
	private String getFolder() { //현재 오늘날짜를 기준으로 경로를 잡아 문자열로 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
	
	private boolean checkImageType(File file) { //특정 파일이 이미지인지 확인
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
