package com.stream.log.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import com.google.gson.Gson;
import com.stream.log.model.StreamLog;
/*
 * This class used to create a large file to test the performance
 */
public class FileGenerator {

	public static void main(String[] args) throws FileNotFoundException, UnknownHostException, InterruptedException {
		PrintWriter pw = new PrintWriter("C:\\Vicky\\file4.txt");
		StreamLog log = new StreamLog("", "state", "type", InetAddress.getLocalHost().getHostName(), 0l, false);

		Gson gs = new Gson();
		for (int i = 0; i <= 10000000; i++) {

			String uuid = UUID.randomUUID().toString();
			log.setId(uuid);
			log.setTimestamp(System.currentTimeMillis());
			pw.write(gs.toJson(log) + "\n");
			try {
				Thread.sleep(i % 5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.setId(uuid);
			log.setTimestamp(System.currentTimeMillis());
			pw.write(gs.toJson(log) + "\n");

		}
		pw.close();

	}

}
