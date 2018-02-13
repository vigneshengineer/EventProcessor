package com.stream.log.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Encryptor {

	public static void main(String[] arg) {
		
//		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//	    encryptor.setPassword("ALERT_LOG_STREAM");
//	    String encryptedPassword = encryptor.encrypt("mydb");
//	    System.out.println(encryptedPassword);
		
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword("ALERT_LOG_STREAM");
		System.out.println(decryptor.decrypt("IbKS+pMs4sVUxjOfGdGqig=="));;
	}
}
