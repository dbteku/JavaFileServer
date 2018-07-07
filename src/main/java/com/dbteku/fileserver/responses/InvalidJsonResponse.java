package com.dbteku.fileserver.responses;

public class InvalidJsonResponse extends HttpResponse{

	public InvalidJsonResponse() {
		super(false, true, "Invalid Json!", 400);
	}
	
}
