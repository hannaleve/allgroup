package com.groupware.service;

import static org.junit.Assert.assertNotNull;

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
public class NoticeServiceTest {

	@Setter(onMethod=@__({@Autowired}))
	private NoticeService service;

	@Ignore
	@Test
	public void testExist() {
		log.info(service);
		assertNotNull(service);
	}

	@Ignore
	@Test
	public void testInsert() { //등록테스트

		NoticeVO notice = new NoticeVO();
		notice.setUSER_NAME("강강찬");
		notice.setNOT_TITLE("2번째 등록 테스트");
		notice.setNOT_CONTENT("내용입니당");
		notice.setNOT_COUNT(2);
		notice.setUSER_NO(2L);

		service.register(notice);

		log.info(notice);
	}

//	@Test
//	public void testGetList() { //목록테스트
//		service.getList().forEach(notice -> log.info(notice));
//	}

	@Ignore
	@Test
	public void testRead() { //조회테스트
		NoticeVO n = service.get(26L);

		log.info(n);
	}

	@Ignore
	@Test
	public void testUpdate() { //수정테스트
		NoticeVO n = service.get(27L);

		if(n == null) {
			return;
		}

		n.setNOT_CONTENT("내욜을 수정해요!");

		log.info("수정완료 : " + service.modify(n)); //true,false
	}


	@Ignore
	@Test
	public void testDelete() { //삭제테스트
		log.info("삭제완료: " + service.remove(27L));//true,false
	}
	
	@Test
	public void testGetList() {
		service.getList(new Criteria(2,10)).forEach(notice -> log.info(notice));
	}
}
