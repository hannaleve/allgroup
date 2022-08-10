package com.groupware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.groupware.common.Criteria;
import com.groupware.vo.ReplyVO;

public interface ReplyMapper {

	public int insert(ReplyVO replyVO); //댓글등록
	
	public ReplyVO read(Long rno); //댓글읽기(특정 댓글)
	
	public int delete(Long rno); //댓글삭제

	public int update(ReplyVO replyVO); //댓글수정
	
	public List<ReplyVO> getListWithPaging( 
			//두 개이상 파라미터 전달위해 사용함
			//xml에서 #{} 와 매칭하여 처리
			@Param("criteria") Criteria criteria,
			@Param("NOT_NO") Long NOT_NO);
	
	public int getCountByNOT_NO(Long not_no); //댓글 전체 수(해당게시글에해당하는)
}
