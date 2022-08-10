package com.groupware.dto;

import com.groupware.common.Criteria;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO { //페이징처리
	
	private int startPage; //시작페이지
	private int endPage; //마지막페이지
	private boolean prev,next; //이전,다음
	
	private int total; //전체페이지
	private Criteria criteria; //검색기준(페이지번호,갯수)
	
	public PageDTO(Criteria criteria, int total) {
		this.criteria = criteria;
		this.total = total;

		this.endPage = (int)(Math.ceil(criteria.getPageNum() / 10.0)) * 10; //마지막페이지계산
		this.startPage = this.endPage - 9; //시작번호계산
		
		//전체 데이터수를 이용하여 진짜 끝페이지 몇 번까지 되는지 계산 
		int realEnd = (int) (Math.ceil((total * 1.0) / criteria.getAmount()));
		
		if(realEnd < this.endPage) { //진짜 끝페이지가 마지막페이지보다 작다면 
			this.endPage = realEnd; //마지막페이지에 진짜 끝페이지 대입 - 그럼 마지막페이지가 진짜 끝번호가 되도록
		}
		
		//이전은 시작 번호보다 큰 경우 이전버튼존재
		this.prev = this.startPage > 1;
		//다음은 진짜 끝페이지가 마지막페이지보다 크다면 다음버튼존재
		this.next = this.endPage < realEnd;
	}

}
