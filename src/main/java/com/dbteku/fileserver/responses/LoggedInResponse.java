package com.dbteku.fileserver.responses;

import com.dbteku.fileserver.models.ClientSession;

public class LoggedInResponse extends HttpResponse{

	private ClientSession session;
	
	public LoggedInResponse(ClientSession session) {
		super(true, false, "Logged In!");
		this.session = session;
	}
	
	public ClientSession getSession() {
		return session;
	}
	
}
