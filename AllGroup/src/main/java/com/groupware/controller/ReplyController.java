package com.groupware.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.groupware.common.Criteria;
import com.groupware.dto.ReplyPageDTO;
import com.groupware.service.ReplyService;
import com.groupware.vo.ReplyVO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies/*")
@RestController //메서드 리턴타입으로 사용자가 정의한 클래스타입사용 or json,xml 으로 자동처리도가능
@Log4j
@AllArgsConstructor
public class ReplyController {

	@Setter(onMethod=@__({@Autowired}))
	private ReplyService replyService;
	
	//댓글등록
	@PreAuthorize("isAuthenticated()") //로그인한 사용자만 해당기능 사용할 수 있도록
	@PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public @ResponseBody ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		         //브라우저에서 json타입으로 된 댓글 데이터 전송한다.(produces를 이용하여 JSON방식으로만 데이터처리하고 문자열반환)
				//RequestBody적용하여 JSON 데이터를 ReplyVO 객체로 변환하도록 지정함.
				//서버에서는 댓글 처리 결과가 정상적으로 됐는지 문자열로 알려줌. 
				//-> 데이터와 함께 HTTP 헤더의 상태 메세지와 같이 전달한다. *상태코드와 에러메세지 
				//서버가동 후 postman으로 테스트완료.
		log.info("ReplyVO: " + vo);
		log.info("타는거야마는거야");

		System.out.println("=================");
		System.out.println(vo);
		System.out.println(vo.getREPLY());
		System.out.println(vo.getREPLYER());
		System.out.println(vo.getNOT_NO());

		int insertCount = replyService.register(vo);//댓글 등록

		log.info("Reply INSERT COUNT: " + insertCount);

		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	//댓글목록
//	@GetMapping(value="/pages/{NOT_NO}/{page}",produces= {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
//	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("page")int page, @PathVariable("NOT_NO") Long not_no) {
//		//브라우저에서 page번호와,게시글번호받음
//		log.info("getList.....");
//		Criteria criteria = new Criteria(page,10); //1이라면 1~10까지나올 수 있음. 1페이지
//		log.info(criteria);
//		
//		return new ResponseEntity<List<ReplyVO>>(replyService.getList(criteria, not_no),HttpStatus.OK);
//	}
	
	//댓글목록(댓글수+페이징처리+댓글목록)
	@GetMapping(value="/pages/{NOT_NO}/{page}",produces= {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page")int page, @PathVariable("NOT_NO") Long not_no) {
		//브라우저에서 page번호와,게시글번호받음
		log.info("getList.....");
		log.info("요청: ................. page : " + page + ", NOT_NO : " + not_no);
		
		Criteria criteria = new Criteria(page,10); //1이라면 1~10까지나올 수 있음. 1페이지
		log.info(criteria);
		
		return new ResponseEntity<ReplyPageDTO>(replyService.getListPage(criteria, not_no),HttpStatus.OK);
	}
	
	//댓글조회
	@GetMapping(value="/{RNO}",produces= {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("RNO")Long rno) {
		//브라우저에서 댓글번호받음
		log.info("getRNO: " + rno);
		return new ResponseEntity<ReplyVO>(replyService.get(rno),HttpStatus.OK);
	}
	
	//댓글삭제
	@PreAuthorize("principal.username == #vo.REPLYER") //로그인한 사용자와 현재 파라미터로 전달되는 댓글작성자가 일치하는 지 체크
	@DeleteMapping(value="/{RNO}",produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("RNO")Long rno, @RequestBody ReplyVO vo) {
		//브라우저에서 댓글번호받음
		log.info("remove : " + rno);
		log.info("replyer : " + vo.getREPLYER());
		
		return replyService.remove(rno) == 1 ? new ResponseEntity<String>("success",HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//댓글수정
	@PreAuthorize("principal.username == #replyVO.REPLYER") //로그인한 사용자와 현재 파라미터로 전달되는 댓글작성자가 일치하는 지 체크
	@RequestMapping(method= {RequestMethod.PUT, RequestMethod.PATCH}, value="/{RNO}", consumes="application/json",produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO replyVO, @PathVariable("RNO") Long rno) {
		replyVO.setRNO(rno);
		log.info("rno : " + rno);
		
		log.info("modify : " + replyVO);
		
		return replyService.modify(replyVO) == 1 ? new ResponseEntity<String>("success",HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	 
}
