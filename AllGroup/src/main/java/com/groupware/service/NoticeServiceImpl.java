package com.groupware.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.common.Criteria;
import com.groupware.mapper.NoticeMapper;
import com.groupware.vo.NoticeVO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
@Service //비지니스 로직을 처리할 객체 
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService {
	
	@Setter(onMethod=@__({@Autowired}))
	private NoticeMapper mapper;
	
	@Override
	public void register(NoticeVO noticeVO) {
		log.info("register....." + noticeVO);
		mapper.insert(noticeVO);
		
	}

	@Override
	public boolean modify(NoticeVO noticeVO) {
		log.info("modify.....");
		
		return mapper.update(noticeVO) == 1; //수정 후 1을 반환하기 때문에 1일경우 true
	}

//	@Override
//	public List<NoticeVO> getList() {
//		log.info("list.....");
//		
//		return mapper.getList();
//	}

	@Override
	public NoticeVO get(Long not_no) {
		log.info("get....." + not_no);
		return mapper.read(not_no);
	}

	@Override
	public boolean remove(Long not_no) {
		log.info("remove.....");
		
		return mapper.delete(not_no) == 1; //삭제 후 1을 반환하기 때문에 1일경우 true
	}

	@Override
	public int updateViewCnt(Long not_no) {
		return mapper.updateViewCnt(not_no);
	}

	@Override
	public List<NoticeVO> getList(Criteria criteria) { //목록(페이징처리,검색조건)
		log.info("get List with criteria: " + criteria);
		return mapper.getListWithPaging(criteria);
	}

	@Override
	public int getTotal(Criteria criteria) {
		log.info("get total count");
		return mapper.getTotalCount(criteria);
	}

}
