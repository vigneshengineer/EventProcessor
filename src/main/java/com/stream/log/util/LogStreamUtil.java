package com.stream.log.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.stream.log.service.impl.LogExecutorServiceImpl;

public class LogStreamUtil {
	private static final Logger logger = LoggerFactory.getLogger(LogStreamUtil.class);
	public static AtomicInteger totalEvents = new AtomicInteger(0);
	public static AtomicInteger invalidEvents = new AtomicInteger(0);
	public static AtomicInteger insertedEvents = new AtomicInteger(0);
	
	/*
	 * load the application context 
	 */
	public static ApplicationContext getContext() {
		ApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/resources/application-context.xml");
		return context;
	}

	public static long computeDuration(long timestamp1, long timestamp2) {
		return Math.abs(timestamp1 - timestamp2);
	}
/*
 * Print the application stats
 * Total events proceed from file
 * Invalid events which is not processed
 * Orphan event which is missing either finish or start state
 * Total events successfully inserted
 */
	public static void exitCheck(long startTime) {
		
		logger.info("=========== Total Event counts ===================:: {}",LogStreamUtil.totalEvents);
		logger.info("=========== Total Invalid counts =================:: {}",LogStreamUtil.invalidEvents);
		logger.info("=========== Orphan Event counts ==================:: {} {}",LogExecutorServiceImpl.value.keySet().size() , LogExecutorServiceImpl.value.keySet().size()>0?LogExecutorServiceImpl.value.keySet():"");
		logger.info("=========== Inserted Event counts=================:: {}",LogStreamUtil.insertedEvents);
		logger.info("Time taken for execution :: {} ms",System.currentTimeMillis()-startTime);
		logger.info("===========Exit=================");;
	}
}
