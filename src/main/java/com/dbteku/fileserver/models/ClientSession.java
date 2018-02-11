package com.dbteku.fileserver.models;

public class ClientSession {

	private String id;
	private long expireTime;
	
	public ClientSession(String id, long expireTime) {
		this.id = id;
		this.expireTime = expireTime;
	}
	
	public String getId() {
		return id;
	}
	
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	
	public long getExpireTime() {
		return expireTime;
	}
	
}
