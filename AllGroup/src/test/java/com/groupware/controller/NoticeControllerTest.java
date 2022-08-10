package com.groupware.controller;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"}) //controller를 scan
@Log4j
@WebAppConfiguration
public class NoticeControllerTest {

	@Setter(onMethod=@__({@Autowired}))
	private WebApplicationContext ctx; //servlet의 servlet-context를 사용하기 위함 
	
	private MockMvc mockMvc; //가짜 MVC라고 생각하기... 가짜로 url와 파라미터 등을 브라우저에서 사용하는 것처럼 만들어서 controller로 실행하기 위함 
	
	
	@Before //모든 테스트 전에 매번 실행하는 것 
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	
	@Ignore
	@Test
	public void testList() throws Exception {
		
		log.info(
				mockMvc.perform(MockMvcRequestBuilders.get("/notice/list")) //get방식 이용 
				.andReturn() //결과 반환해서 model에 어떤 데이터들이 담아있는지 확인 위함 
				.getModelAndView()
				.getModelMap());
	}
	

	@Test
	public void testRegister() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/notice/register")
				.param("NOT_TITLE", "테스트 새글 제목")
				.param("NOT_CONTENT","태스트 새글 내용")
				.param("USER_NAME", "고양이")
	).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);

	}
	
	@Ignore
	@Test
	public void testGet() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/notice/read")
				.param("NOT_NO", "29"))
				.andReturn()
				.getModelAndView().getModelMap()
				);
	}
	
	@Ignore
	@Test
	public void testModify() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/notice/modify")
				.param("NOT_NO", "29")
				.param("NOT_TITLE", "테스트 수정 제목")
				.param("NOT_CONTENT","태스트 수정 내용")
				.param("USER_NAME", "강아지")
				.param("NOT_COUNT", "1")
	).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);

	}
	
	@Ignore
	@Test
	public void testRemove() throws Exception {
		//삭제 전 db 게시글 번호 확인
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/notice/remove")
				.param("NOT_NO", "1"))
				.andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	@Ignore
	@Test
	public void testListPaging() throws Exception { //1페이지의 10개 데이터 목록 출력 테스트 
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/notice/list")
				.param("pageNum", "1")
				.param("amount", "10"))
				.andReturn()
				.getModelAndView()
				.getModelMap()
				);
	}
}
