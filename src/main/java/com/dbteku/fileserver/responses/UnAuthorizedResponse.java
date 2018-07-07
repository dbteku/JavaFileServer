package com.dbteku.fileserver.responses;

public class UnAuthorizedResponse extends HttpResponse{

	public UnAuthorizedResponse() {
		super(false, true, "You are not authorized!", 401);
	}
	
}
