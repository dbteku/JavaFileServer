package com.dbteku.fileserver.responses;

public class DoesntExistResponse extends HttpResponse{

	public DoesntExistResponse() {
		super(false, true, "There is no client by that id!");
	}
	
}
