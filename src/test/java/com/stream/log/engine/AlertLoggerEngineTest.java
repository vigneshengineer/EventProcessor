package com.stream.log.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * @author vignesh
 * Test class to execute the log events with hsqldb
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/application-context.xml")
public class AlertLoggerEngineTest {

	private static final Logger logger = LoggerFactory.getLogger(AlertLoggerEngineTest.class);
	
	
	@Before
	public void setUp() throws Exception {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
	}
	
	/*
	 * Create connection to the DB
	 */
	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:file:AlertStream_DB/HSQLDB_TEST", "SA", "mydb");
	}


	/*
	 * Drop the table and close connection
	 */
	@AfterClass
	public static void destroy() throws SQLException, ClassNotFoundException, IOException {

	        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
	            statement.executeUpdate("DROP TABLE LOG");
	            connection.commit();
	            connection.close();
	        }
	 }

	/*
	 * Execute the main test script
	 */
	@Test
	public void executeTest() {
		logger.info("Executing Test");
		AlertLoggerEngine.run("src\\main\\resources\\log.txt"); //Sample Test Data
	}
	
	/*
	 * Execute the test script to get the total events inserted
	 */
	@Test
	public void countTotatlEvents()
	{
		try
		{
			int count=countEvents();
			logger.info("Total Events : "+count);
			assertFalse(countEvents()<0);
		}catch(Exception e)
		{
			logger.error("Error while fetching the count "+e);
		}
	}
	
	/*
	 * Execute the test script to get the total delayed events inserted
	 */
	@Test
	public void countTotatDelayedlEvents()
	{
		try
		{
			int count=countDelayedEvents();
			logger.info("Total Delayed Events : "+count);
			assertTrue(countDelayedEvents()>0);
		}catch(Exception e)
		{
			logger.error("Error while fetching the delayed event count "+e);
		}
	}
	
	
	/**
	 * @return
	 * @throws SQLException
	 * Count total events
	 */
	public int countEvents() throws SQLException
	{
		Statement statement = getConnection().createStatement();
		ResultSet result=statement.executeQuery("SELECT COUNT(ID) as total from LOG");
		if(result.next())
		{
			return result.getInt("total");

		}
		return 0;
	}
	
	/**
	 * @return
	 * @throws SQLException
	 * Count total delayed events
	 */
	public int countDelayedEvents() throws SQLException
	{
		Statement statement = getConnection().createStatement();
		ResultSet result=statement.executeQuery("SELECT COUNT(ID) as total from LOG where alert=true");
		if(result.next())
		{
			return result.getInt("total");

		}
		return 0;
	}
}
