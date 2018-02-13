package com.stream.log.service;

import com.stream.log.model.StreamLog;
/*
 * Service layer interface
 */
public interface LogExecutorService {

	public void addEvent(StreamLog log) throws Exception;
}
