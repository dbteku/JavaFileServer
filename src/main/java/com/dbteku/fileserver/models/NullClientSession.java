package com.dbteku.fileserver.models;

public class NullClientSession extends ClientSession{

	public NullClientSession() {
		super("NULL", "NULL", 0);
	}
	
	@Override
	public boolean isNull() {
		return true;
	}
	
}
