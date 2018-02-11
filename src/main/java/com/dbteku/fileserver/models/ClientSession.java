package com.dbteku.fileserver.models;

import com.dbteku.fileserver.interfaces.INullable;

public class ClientSession implements INullable{

	private String id;
	private String username;
	private transient long expireTime;
	
	public ClientSession(String id, String username, long expireTime) {
		this.id = id;
		this.username = username;
		this.expireTime = expireTime;
	}
	
	public String getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	
	public long getExpireTime() {
		return expireTime;
	}
	
}
