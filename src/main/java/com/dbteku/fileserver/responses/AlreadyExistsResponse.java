package com.dbteku.fileserver.responses;

public class AlreadyExistsResponse extends HttpResponse{

	public AlreadyExistsResponse() {
		super(false, true, "A client by that name already exists!");
	}
	
}
