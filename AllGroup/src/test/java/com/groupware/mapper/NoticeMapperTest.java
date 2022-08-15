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
import com.groupware.vo.NoticeVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class NoticeMapperTest {

	@Setter(onMethod=@__({@Autowired}))
	private NoticeMapper mapper;

	@Ignore
	@Test
	public void testGetList() { //목록테스트
		mapper.getList().forEach(notice -> log.info(notice));
		//mapper.getList().forEach(notice -> System.out.println(notice));
	}

	@Ignore
	@Test
	public void testInsert() { //등록테스트(더미100개)
		IntStream.rangeClosed(1,100).forEach(i -> {
		NoticeVO notice = new NoticeVO();
		notice.setUSER_NAME("user" + i);
		notice.setNOT_TITLE("title" + i);
		notice.setNOT_CONTENT("content" + i);
		mapper.insert(notice);
		});
		
	}

	@Ignore
	@Test
	public void testRead() { //조회테스트
		NoticeVO n = mapper.read(25L);

		log.info(n);
	}

	@Ignore
	@Test
	public void testDelete() {//삭제테스트
		log.info("delete count : " + mapper.delete(25L));
	}

	@Ignore
	@Test
	public void testUpdate() { //수정테스트

		NoticeVO noticeVO = new NoticeVO();

		noticeVO.setNOT_NO(1L);
		noticeVO.setNOT_TITLE("수정해봅니당");
		noticeVO.setNOT_CONTENT("수정합니다!");

		int count = mapper.update(noticeVO);
		log.info("update count : " + count);
		log.info(noticeVO);
	}
	

	@Ignore
	@Test
	public void testPaging() {
		Criteria criteria = new Criteria();
		
		//10개씩 3페이지
		//한 페이지당 10개씩 출력하는 3페이지에 해당하는 데이터 
		criteria.setPageNum(3);
		criteria.setAmount(10);
		
		List<NoticeVO> list = mapper.getListWithPaging(criteria);
		list.forEach(Notice -> log.info(Notice.getNOT_NO()));
	}
	
	@Ignore
	@Test
	public void testSearch() {
		Criteria criteria = new Criteria();
		criteria.setKeyword("e");
		criteria.setType("TC");
		
		List<NoticeVO> list = mapper.getListWithPaging(criteria);
		
		list.forEach(notice -> log.info(notice));
	}


	
}

