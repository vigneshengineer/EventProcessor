package com.stream.log.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stream.log.executor.LogExecutor;
import com.stream.log.model.StreamLog;
import com.stream.log.service.LogExecutorService;
import com.stream.log.util.LogStreamUtil;
/*
 * Service layer Implementation
 */
@Service("logExecutorServiceImpl")
public class LogExecutorServiceImpl implements LogExecutorService {

	@Autowired
	private LogExecutor logExecutor;
	
	//For parallel process of the data insertion and store the event id as key
	public static Map<String, StreamLog> value = new ConcurrentHashMap<>(); 
	
	//Configurable threshold for the event delay
	@Value("${duration.threshold}")
	private int threshold;

	public void addEvent(StreamLog log) throws Exception {
		if (!value.containsKey(log.getId())) { //if the event not present in the map insert it. i.e if either STARTED or FINISHED state is not there
			value.put(log.getId(), log);
		} else {
			//Both start and finish event are present
			long duration = LogStreamUtil.computeDuration(log.getTimestamp(), value.get(log.getId()).getTimestamp()); //Calculate the duration diff
			if (duration > threshold) {
				log.setAlert(true); //if duration is greater than 4 ms, flag the event
			}
			log.setDuration(duration);
			value.remove(log.getId()); // Remove the event from the map so that we can find the orphan records
			logExecutor.executeLogger(log);
		}
	}

}
