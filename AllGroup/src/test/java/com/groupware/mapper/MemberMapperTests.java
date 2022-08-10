package com.groupware.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		 "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class MemberMapperTests {


	@Setter(onMethod=@__({@Autowired}))
	private PasswordEncoder encoder;
	
	@Setter(onMethod=@__({@Autowired}))
	private DataSource dataSource;
	
	@Test
	public void testInsertMember() { //임시회원추가
		String sql = "insert into tbl_member(USER_ID,USER_SNO,USER_PWD,USER_NAME,USER_RANK,USER_EMAIL,USER_PHONE,USER_ADDRES,DEPT_NAME) values (?,?,?,?,?,?,?,?,?)";
		
		for(int i=0; i<100; i++) {
			Connection con = null;
			PreparedStatement pstm = null;
			
			try {
				con = dataSource.getConnection();
				pstm = con.prepareStatement(sql);
				
				pstm.setString(3, encoder.encode("pw"+i));
				
				if(i < 80) {
					pstm.setString(1, "user"+i);
					pstm.setString(2, "20220322_S");
					pstm.setString(4, "회원"+i);
					pstm.setString(5, "사원");
					pstm.setString(6, "eee@naver.com");
					pstm.setString(7, "010-1111-1111");
					pstm.setString(8, "서울시종로구");
					pstm.setString(9, "개발팀");
				
				}else {
					pstm.setString(1, "admin"+i);
					pstm.setString(2, "20220322_A");
					pstm.setString(4, "관리자"+i);
					pstm.setString(5, "서버관리자");
					pstm.setString(6, "fff@google.com");
					pstm.setString(7, "010-2222-2222");
					pstm.setString(8, "경기도하님시");
					pstm.setString(9, "인프라팀");
				}
				
				pstm.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if((pstm != null) ) {
					try {
						pstm.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if((con != null)) {
					try {
						con.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
	}
	
	
	@Test
	public void testInsertAuth() { //임시 회원권한추가
		String sql = "insert into tbl_member_auth (user_id,auth) values(?,?)";
		
		for(int i=0; i<100; i++ ) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = dataSource.getConnection();
				pstmt = con.prepareStatement(sql);
				
				if(i < 80) {
					pstmt.setString(1, "user"+i);
					pstmt.setString(2, "ROLE_USER");
				}else {
					pstmt.setString(1, "admin"+i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				pstmt.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if((pstmt != null) ) {
					try {
						pstmt.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if((con != null)) {
					try {
						con.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
	}
}
