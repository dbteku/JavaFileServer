package com.dbteku.fileserver.responses;

public class InvalidUsernameOrPassword extends HttpResponse{

	public InvalidUsernameOrPassword() {
		super(false, true, "Username or password was invalid!", 400);
	}
	
}
