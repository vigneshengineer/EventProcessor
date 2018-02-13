package com.stream.log.dao;

import com.stream.log.model.StreamLog;
/*
 * Data layer interface
 */
public interface LogExecutorDAO {

	public void addEvent(StreamLog log);
}
