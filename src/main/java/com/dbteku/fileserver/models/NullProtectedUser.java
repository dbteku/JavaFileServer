package com.dbteku.fileserver.models;

public class NullProtectedUser extends ProtectedUser{
	
	public NullProtectedUser() {
		super("NULL", "NULL", "NULL");
	}
	
	@Override
	public boolean isNull() {
		return true;
	}
	
}
