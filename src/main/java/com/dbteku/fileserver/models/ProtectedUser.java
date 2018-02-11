package com.dbteku.fileserver.models;

import com.dbteku.fileserver.interfaces.INullable;

public class ProtectedUser implements INullable{

	private String username;
	private String hashedPassword;
	private String saltCode;
	
	public ProtectedUser(String username, String hashedPassword, String saltCode) {
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.saltCode = saltCode;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public String getSaltCode() {
		return saltCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		
		try {
			ProtectedUser toCheck = (ProtectedUser) obj;
			equals = toCheck.username.equalsIgnoreCase(username) &&
					toCheck.hashedPassword.equals(hashedPassword);
		}catch(ClassCastException e) {
			e.printStackTrace();
		}
		
		return equals;
	}
	
}
