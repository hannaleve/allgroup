package com.groupware.service;

import java.util.List;

import com.groupware.common.Criteria;
import com.groupware.dto.ReplyPageDTO;
import com.groupware.vo.ReplyVO;

public interface ReplyService {

	public int register(ReplyVO replyVO);
	
	public ReplyVO get(Long rno);
	
	public int modify(ReplyVO replyVO);
	
	public int remove(Long rno);
	
	public List<ReplyVO> getList(Criteria criteria, Long not_no);
	
	public ReplyPageDTO getListPage(Criteria criteria, Long not_no); //댓글 전체 수와 댓글목록 반환
}
