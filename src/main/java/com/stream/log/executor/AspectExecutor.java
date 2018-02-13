package com.stream.log.executor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Spring AOP to implement after throw aspect for the package com.stream.log
 */
@Aspect
public class AspectExecutor {

	private static final Logger logger = LoggerFactory.getLogger(AspectExecutor.class);
	
    
	@AfterThrowing(pointcut = "within(com.stream.log..*)", throwing= "error")  
    public void errorAdvice(JoinPoint jp,Throwable error) throws Exception
    {  		
			logger.error("Exception in Method ::"+jp.getSignature());   	
			logger.error("Error processing event stream ::"+error.getMessage());  
    }  
	
	@AfterThrowing(pointcut = "execution(* com.stream.log.dao.impl.LogExecutorDAOImpl.*(..))", throwing= "error")  
    public void errorAdviceDao(JoinPoint jp,Throwable error) throws Exception
    {  		
			logger.error("Exception in Method ::"+jp.getSignature());   	
			logger.error("Error access database ::"+error.getMessage());  
    }  
}
