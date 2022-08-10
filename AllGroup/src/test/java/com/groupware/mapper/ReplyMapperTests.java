package com.groupware.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.groupware.common.Criteria;
import com.groupware.vo.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {

	@Setter(onMethod=@__({@Autowired}))
	private ReplyMapper mapper;
	
	//게시물 번호
	private Long[] NOT_NO_Arr = {189L,188L,187L,186L,185L};
	
//	@Ignore
//	@Test
//	public void testCreate() {
//		IntStream.rangeClosed(1, 10).forEach(i -> {
//			
//			ReplyVO replyVO = new ReplyVO();
//			
//			//게시글 번호
//			replyVO.setNOT_NO(NOT_NO_Arr[i % 5]);
//			replyVO.setREPLY("댓글 테스트" + i);
//			replyVO.setREPLYER("replyer" + i);
//			
//			mapper.insert(replyVO);
//			
//			log.info(mapper);
//		});
//	}
//	
//	
//	@Test
//	public void testRead() {
//		
//		Long targerRNO = 5L;
//		
//		ReplyVO replyVO = mapper.read(targerRNO);
//		
//		log.info(replyVO);
//	}
//	
//	@Test
//	public void testDelete() {
//		Long targetRNO = 1L;
//		mapper.delete(targetRNO);
//	}
//	
//	@Test
//	public void testUpdate() {
//		Long targetRno = 10L;
//		ReplyVO replyVO = mapper.read(targetRno);
//		
//		replyVO.setREPLY("update reply...");
//		
//		int count = mapper.update(replyVO);
//		
//		log.info("UPDATE COUNT : " + count);
//	}
//	
//	@Test
//	public void testList() {
//		Criteria criteria = new Criteria();
//		
//		//189L
//		//189번에 해당하는 게시글의 댓글목록
//		List<ReplyVO> replies = mapper.getListwithPaging(criteria, NOT_NO_Arr[0]);
//		
//		replies.forEach(reply -> log.info(reply));
//	}
	
//	@Test
//	public void testList2() { //댓글페이징잘되는지 테스트
//		Criteria criteria = new Criteria();
//		List<ReplyVO> replies = mapper.getListwithPaging(criteria,185L);
//		replies.forEach(i -> log.info(i));
//	}
}
