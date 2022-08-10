package com.groupware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.groupware.common.Criteria;
import com.groupware.vo.NoticeVO;

public interface NoticeMapper {

//	@Select("select * from tbl_notice where not_no > 0")
	public List<NoticeVO> getList();
	
	public List<NoticeVO> getListWithPaging(Criteria criteria); //페이징처리추가
	
	public int getTotalCount(Criteria criteria); //전체데이터 갯수처리
	
	public void insert(NoticeVO notice);
	
	public NoticeVO read(Long not_no);
	
	public int delete(Long not_no);
	
	public int update(NoticeVO noticeVO);
	
	public int updateViewCnt(Long not_no); 
	
	public void updateReplyCnt(@Param("NOT_NO") Long NOT_NO, @Param("amount") int amount); //댓글 등록 시 1 댓글 삭제 시 -1
}
