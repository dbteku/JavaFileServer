package com.dbteku.fileserver.models;

public class NewUser{

	private String username;
	private String password;
	
	public NewUser(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
}
