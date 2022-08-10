package com.groupware.dto;

import java.util.List;

import com.groupware.vo.ReplyVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class ReplyPageDTO {

	private int replyCnt; //댓글전체 수
	private List<ReplyVO> list; //댓글목록
	
	
}
