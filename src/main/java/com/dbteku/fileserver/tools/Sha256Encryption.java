package com.dbteku.fileserver.tools;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class Sha256Encryption {

	
	private MessageDigest digest;
	
	public Sha256Encryption() {
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public String encrypt(String password) {
		String hashedPassword = "";
		if(digest != null) {
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));	
			hashedPassword = new String(Hex.encodeHex(hash));
		}
		return hashedPassword;
	}
	
}
