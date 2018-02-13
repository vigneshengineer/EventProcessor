package com.stream.log.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.stream.log.model.StreamLog;
import com.stream.log.service.LogExecutorService;
import com.stream.log.util.LogStreamUtil;

/*
 * 
 * Author Vignesh Devendran
 * This application fetch each line from the file passed as a event, then process to find the delayed events. Flag the delayed events more than 
 * 4 ms and store the record to the file based HSQLDB 
 *
 */
public class AlertLoggerEngine {
	private static Gson gson = new Gson();

	private static final Logger logger = LoggerFactory.getLogger(AlertLoggerEngine.class);

	/*
	 * Read the file input passed from arg
	 * 
	 * gradle run -PappArgs="['<file path>']"
	 */
	public static void main(String arg[]) throws IOException {
		logger.info("Entering FileEninge.Main()");
		AlertLoggerEngine.run(arg);
	}

	/*
	 * Use parallel stream to process the file in parallel by utilizing multiple CPU core
	 */
	public static void run(String... args) {
		String fileName = args[0];		
		long startTime = System.currentTimeMillis();
		LogExecutorService service = (LogExecutorService) LogStreamUtil.getContext().getBean("logExecutorServiceImpl");
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.parallel().forEach(line -> {
				try {
					LogStreamUtil.totalEvents.getAndIncrement(); //To check the number of lines processed
					service.addEvent(gson.fromJson(line, StreamLog.class)); //Store the processed events to DB

				} catch (Exception e) {
					LogStreamUtil.invalidEvents.incrementAndGet();
					logger.error("Execption while parsing the event {} :: ", line, e);
				}
			});
			LogStreamUtil.exitCheck(startTime); //To Print the statistics of the file processed
		} catch (IOException e) {
			logger.error("Execption while processin the file:: ",e);
		}
	}

}
