package com.stream.log.dao.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.stream.log.dao.LogExecutorDAO;
import com.stream.log.model.StreamLog;
import com.stream.log.util.LogStreamUtil;

/*
 * Data layer Implementation
 * This class will insert the processed event into a file based db
 */
@Repository("logExecutorDAOImpl")
public class LogExecutorDAOImpl implements LogExecutorDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate; //Spring JDBC template

	@Value("${insert.query}")
	private String insertQuery;

	@Value("${create.table.query}")
	private String createTableQuery;

	private static final Logger logger = LoggerFactory.getLogger(LogExecutorDAOImpl.class);

	/*
	 * Create table during spring bean life cycle phase.
	 */
	@PostConstruct
	public void createTable() {
		jdbcTemplate.execute(createTableQuery);
	}

	/*
	 * (non-Javadoc)
	 * @see com.stream.log.dao.LogExecutorDAO#addEvent(com.stream.log.model.StreamLog)
	 * 
	 * The method will run asynchronously(new threads will be created and closed for each insertion). taskExecutor configured in application-context.xml
	 * 
	 */
	@Override
	@Async("taskExecutor")
	public void addEvent(StreamLog log) {
		try {
			jdbcTemplate.update(insertQuery,
					new Object[] { log.getId(),log.getDuration(), log.getType(), log.getHost(), log.isAlert() }); //Insert the event to DB
			LogStreamUtil.insertedEvents.incrementAndGet(); //Increment to check the inserted event stats
		}catch (Exception e) {
			logger.error("Error while inserting event :: {}", log.getId()+" "+e);
		}

	}
	
	

}
