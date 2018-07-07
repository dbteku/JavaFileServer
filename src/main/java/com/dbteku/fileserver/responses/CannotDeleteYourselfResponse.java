package com.dbteku.fileserver.responses;

public class CannotDeleteYourselfResponse extends HttpResponse{

	public CannotDeleteYourselfResponse() {
		super(false, true, "You cannot delete yourself!", 403);
	}
	
}
