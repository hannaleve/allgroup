package com.groupware.service;

import java.util.List;

import com.groupware.common.Criteria;
import com.groupware.vo.NoticeVO;

public interface NoticeService {
	
	public void register(NoticeVO noticeVO); //등록
	
	public NoticeVO get(Long not_no); //조회
	
	public boolean modify(NoticeVO noticeVO); //수정
	
//	public List<NoticeVO> getList(); //목록
	
	public List<NoticeVO> getList(Criteria criteria); //목록(페이징처리)
	
	public int getTotal(Criteria criteria); //전체데이터갯수
	
	public boolean remove(Long not_no); //삭제
	
	public int updateViewCnt(Long not_no); //조회수 
}
