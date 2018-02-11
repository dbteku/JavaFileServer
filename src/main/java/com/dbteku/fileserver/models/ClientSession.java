package com.dbteku.fileserver.models;

import com.dbteku.fileserver.interfaces.INullable;

public class ClientSession implements INullable{

	private String id;
	private transient long expireTime;
	
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
