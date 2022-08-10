package com.groupware.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.groupware.common.Criteria;
import com.groupware.dto.PageDTO;
import com.groupware.service.NoticeService;
import com.groupware.vo.NoticeVO;
import com.groupware.vo.PhotoVO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/notice/*") // /notice로 시작하는 모든 것들 들어온다.
@AllArgsConstructor //생성자 만들고 자동으로 주입하도록 함, 만약 생성자를 만들지 않으면 @Setter(onMethod=@__({@Autowired}))
public class NoticeController { //공지사항

	private NoticeService noticeService;
	
//	@GetMapping("/list") 
//	public void list(Model model) { //공지사항 목록
//		log.info("list...");
//		model.addAttribute("list", noticeService.getList());
//	}
	
	@GetMapping("/list") 
	public void list(Criteria criteria,Model model) { //공지사항 목록
		log.info("list..." + criteria);

		int total = noticeService.getTotal(criteria);
		
		log.info("전체 total : " + total);
		
		model.addAttribute("list", noticeService.getList(criteria)); //공지사항목록들
		model.addAttribute("pageMaker",new PageDTO(criteria,total)); //페이징처리,전체갯수
	}
	
	@GetMapping("/register") 
	@PreAuthorize("isAuthenticated()") //로그인한 사용자만 해당기능 사용할 수 있도록
	public void register() {}
	
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")//로그인한 사용자만 해당기능 사용할 수 있도록
	public String register(NoticeVO noticeVO, RedirectAttributes attributes) { //공지사항 등록
		log.info("register.." + noticeVO);
		noticeService.register(noticeVO);
		attributes.addFlashAttribute("result",noticeVO.getNOT_NO());
		
		return "redirect:/notice/list";
	}
	
	@GetMapping({"/read","/modify"})
	public void get(@RequestParam("NOT_NO") Long not_no, @ModelAttribute("criteria") Criteria cri,Model model) { //공지사항 조회,수정
		log.info("get or modify");
		noticeService.updateViewCnt(not_no); //조회수 증가
		model.addAttribute("notice",noticeService.get(not_no));
	}
	

	@PreAuthorize("principal.username == #noticeVO.USER_NAME") //로그인한 사용자와 현재 파라미터로 전달되는 작성자가 일치하는 지 체크
	@PostMapping("/modify")
	public String modify(NoticeVO noticeVO,@ModelAttribute("criteria") Criteria cri, RedirectAttributes attributes) { //공지사항 수정
		log.info("modify: " + noticeVO);
		
		if(noticeService.modify(noticeVO)) {
			attributes.addFlashAttribute("result","success");
		}
		
		//리다이렉트 시 검색,페이징처리 같이 보내기 용도
//		attributes.addAttribute("pageNum",cri.getPageNum());
//		attributes.addAttribute("amount",cri.getAmount());
//		attributes.addAttribute("type",cri.getType());
//		attributes.addAttribute("keyword",cri.getKeyword());
		return "redirect:/notice/list" + cri.getListLink(); 
	}
	
	@PreAuthorize("principal.username == #USER_NAME") //로그인한 사용자와 작성자가 일치하는 지 체크
	@PostMapping("/remove")
	public String remove(@RequestParam("NOT_NO") Long not_no,Criteria cri, RedirectAttributes attributes,String USER_NAME) { //공지사항 삭제
		log.info("remove....");
		
		if(noticeService.remove(not_no)) {
			attributes.addFlashAttribute("result","success");
			log.info("삭제성공!");
		}
		
		//리다이렉트 시 검색,페이징처리 같이 보내기 용도
//		attributes.addAttribute("pageNum",cri.getPageNum());
//		attributes.addAttribute("amount",cri.getAmount());
//		attributes.addAttribute("type",cri.getType());
//		attributes.addAttribute("keyword",cri.getKeyword());
		return "redirect:/notice/list" + cri.getListLink(); 
	}

}
