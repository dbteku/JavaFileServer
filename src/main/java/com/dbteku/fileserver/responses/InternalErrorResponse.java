package com.dbteku.fileserver.responses;

public class InternalErrorResponse extends HttpResponse{

	public InternalErrorResponse() {
		super(false, true, "An internal error occured!", 500);
	}
	
}
