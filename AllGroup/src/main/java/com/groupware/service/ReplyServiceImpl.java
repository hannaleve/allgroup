package com.groupware.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groupware.common.Criteria;
import com.groupware.dto.ReplyPageDTO;
import com.groupware.mapper.NoticeMapper;
import com.groupware.mapper.ReplyMapper;
import com.groupware.vo.ReplyVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Service
@Log4j
public class ReplyServiceImpl implements ReplyService {

	@Setter(onMethod=@__({@Autowired}))
	private ReplyMapper mapper;
	
	@Setter(onMethod=@__({@Autowired}))
	private NoticeMapper mapper2;
	
	@Transactional
	@Override
	public int register(ReplyVO replyVO) {
		log.info("등록...." + replyVO);
		System.out.println("등록되낭..?" + replyVO);
		
		mapper2.updateReplyCnt(replyVO.getNOT_NO(), 1); //댓글수증가
		return mapper.insert(replyVO);
	}

	@Override
	public ReplyVO get(Long rno) {
		log.info("조회...." + rno);
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVO replyVO) {
		log.info("수정...." + replyVO);
		return mapper.update(replyVO);
	}

	@Transactional
	@Override
	public int remove(Long rno) {
		log.info("삭제...." + rno);
		ReplyVO v = mapper.read(rno);
		mapper2.updateReplyCnt(v.getNOT_NO(), -1);//댓글 삭제 시 게시물에 해당하는 댓글 수 삭제 
		return mapper.delete(rno);
	}

	@Override
	public List<ReplyVO> getList(Criteria criteria, Long not_no) {
		log.info("목록조회...." + not_no); //게시글 조회
		
		return mapper.getListWithPaging(criteria, not_no);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria criteria, Long not_no) { //댓글 전체 수와 댓글 목록(페이징)
		return new ReplyPageDTO(mapper.getCountByNOT_NO(not_no), mapper.getListWithPaging(criteria, not_no));
	}

	
}
