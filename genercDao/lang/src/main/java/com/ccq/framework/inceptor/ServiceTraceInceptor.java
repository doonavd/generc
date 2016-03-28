package com.ccq.framework.inceptor;
/**
 *  @author xiaoliu
 *  
 *  ҵ������������
 *  
 */

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ccq.framework.exception.AppException;
import com.ccq.framework.lang.Result;

@Component
@Aspect
public class ServiceTraceInceptor {

	public static Logger logger = LoggerFactory.getLogger(ServiceTraceInceptor.class);
	
	@Around("@within(com.ccq.framework.annotation.ServiceTrace)")
	public Object methodInceptor(ProceedingJoinPoint pjp) {
		Object result = null;
		try {
			result = pjp.proceed();
		} catch (Throwable ex) {
			
			if(ex instanceof AppException) {
				logger.debug(ex.toString());
				Result r = new Result(false,((AppException)ex).getMessage(),((AppException)ex).getCode());
				return r;
			}else {
				//������쳣
				logger.debug(ex.toString());
				Result r = new Result(false,"���ݷ����쳣","DATA_ACCESS_EXCEPTION");
				return r;
			}
		}
		
		return result;
	}
}
