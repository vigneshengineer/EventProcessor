package com.stream.log.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stream.log.dao.LogExecutorDAO;
import com.stream.log.model.StreamLog;

@Component("logExecutor")
public class LogExecutor {

	@Autowired
	private LogExecutorDAO logExecutor;
	
	ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public void executeLogger(StreamLog log)
	{		
		 logExecutor.addEvent(log); //Spring frame work will shutdown the thread processed asynchronously
		//executor.execute(() -> logExecutor.addEvent(log));	
	}
	
}
