package com.groupware.common;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

import lombok.extern.log4j.Log4j;

@Log4j
public class UploadFile {

	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception { //파일업로드 

		UUID uid = UUID.randomUUID();

		String savedName = uid.toString() + "_" + originalName; //uuid_실제파일이름 -> 파일이름 
		
		log.info("파일업로드 파일이름 : " + savedName);
		
		String savedPath = calcPath(uploadPath); //파일경로 + 년,월,일 로 디렉토리 생성 됨 -> 파일경로 완성 

		log.info("파일업로드 파일경로 : " + savedPath);
		
		File target = new File(uploadPath + savedPath, savedName);
		
		log.info("파일업로드 FILE TARGET : " + target);

		FileCopyUtils.copy(fileData, target);

		String formatName = originalName.substring(originalName.lastIndexOf(".") + 1);
		
		log.info("파일업로드 FORMAT NAME : " + formatName);

		String uploadedFileName = null;

		if (Media.getMediaType(formatName) != null) { //JPG,GIF,PNG 타입이라면 (널이 아니라면)
			uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
		} else {
			uploadedFileName = makeIcon(uploadPath, savedPath, savedName); //그 외 엑셀,한글.. 등은 아이콘으로 표시 위함 
		}

		return uploadedFileName;

	}

	public static String uploadEditorFile(String uploadPath, String originalName, byte[] fileData) throws Exception { //에디터업로드 

	    UUID uid = UUID.randomUUID();
	    
	    String savedName = uid.toString() +"_"+originalName; //uuid_실제파일이름 -> 파일이름 
	    
	    log.info("에디터업로드 파일이름 : " + savedName);
	    
	    String savedPath = calcPath(uploadPath); //파일경로 + 년,월,일 로 디렉토리 생성 됨 -> 파일경로 완성 
	    
		log.info("에디터업로드 파일경로 : " + savedPath);
		
	    File target = new File(uploadPath +savedPath,savedName); 
	    
		log.info("에디터업로드 FILE TARGET : " + target);
		
	    FileCopyUtils.copy(fileData, target);
	    
	    String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
	    
	    String uploadedFileName = null;
	    
	    if(Media.getMediaType(formatName) != null){ //JPG,GIF,PNG 타입이라면 (널이 아니라면)
	      uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName); //썸네일로보여주기
	    }else{
	      uploadedFileName = makeIcon(uploadPath, savedPath, savedName); //아이콘으로 보여주기 
	    }
	    
	    return uploadedFileName;

	}

	private static String makeIcon(String uploadPath, String path, String fileName) throws Exception { //아이콘으로처리

		String iconName = uploadPath + path + File.separator + fileName;

		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception { //썸네일처리(s_)

		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));

		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 200);

		String thumbnailName = uploadPath + path + File.separator + "s_" + fileName; //s_ 언더바로 시작하게끔

		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

	private static String calcPath(String uploadPath) { //파일경로를 받아 년,월,일 뽑기 (파일경로+년월일 폴더로 만들기 위함)

		Calendar cal = Calendar.getInstance();

		String yearPath = File.separator + cal.get(Calendar.YEAR);

		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);

		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		makeDir(uploadPath, yearPath, monthPath, datePath);//파일경로 + 년,월,일 

		log.info(datePath);

		return datePath;
	}

	private static void makeDir(String uploadPath, String... paths) { //파일경로 + 년,월,일  디렉토리 생성 

		if (new File(paths[paths.length - 1]).exists()) {
			return;
		}

		for (String path : paths) {

			File dirPath = new File(uploadPath + path);

			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}

}
