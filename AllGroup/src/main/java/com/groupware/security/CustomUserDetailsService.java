package com.groupware.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.groupware.mapper.MemberMapper;
import com.groupware.security.vo.CustomUser;
import com.groupware.vo.MemberVO;

import jdk.internal.org.jline.utils.Log;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService{

	@Setter(onMethod=@__({@Autowired}))
	private MemberMapper mapper;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.warn("Load User By UserName : " + username);
		
		
		//userName means userid
		MemberVO vo = mapper.read(username);
		
		log.warn("queried by member mapper : " + vo); //쿼리 읽어온 부분 
		
		return vo == null? null  : new CustomUser(vo); //CustomUser타입 객체로 변환해서 반환 - CustomUser.java
	}

}
