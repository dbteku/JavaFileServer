package com.dbteku.fileserver.responses;

public class NotFoundResponse extends HttpResponse{

	public NotFoundResponse() {
		super(false, true, "The entity you requested does not exist!", 404);
	}
	
}
