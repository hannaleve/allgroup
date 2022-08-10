package com.groupware.aop;

import org.apache.catalina.tribes.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class NoticeLogsAdvice { //공지사항 로그를 기록해주는 관심사 
	
	@Before("execution(* com.groupware.service.NoticeService*.*(..))") //2
	public void logBefore() {
		log.info("========공지사항로직시작===========");
	}
	
	@AfterThrowing(pointcut = "execution(* com.groupware.service.NoticeService*.*(..))", throwing="exception") //예외발생 시 
	public void logException(Exception exception) {
		log.info(" 공지사항 Exception 발생!!!!! 확인바람 ");
		log.info("Exception : " + exception);
	}
	
	@Around("execution(* com.groupware.service.NoticeService*.*(..))") //먼저 시작 
	public Object logTime(ProceedingJoinPoint pjp) { //파마리터와 target에 대한 정보 출력 ( 메서드 실행 시 ) 
		long start = System.currentTimeMillis();
		
		log.info("Target: " + pjp.getTarget());
		log.info("Param: " + Arrays.toString(pjp.getArgs()));
		
		//invoke method
		Object result = null;
		
		try {
			result = pjp.proceed();
		}catch (Throwable e) {
			log.warn(e);
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		log.info("TIME : " + (end - start)); //실행하는 데 걸린 시간  3번째
		
		return result;
		
		
		
	}


}
