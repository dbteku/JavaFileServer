package com.dbteku.fileserver.responses;

import com.dbteku.fileserver.models.ClientSession;

public class LoggedInResponse extends HttpResponse{

	private ClientSession session;
	
	public LoggedInResponse(ClientSession session) {
		super(true, false, "Logged In!", 200);
		this.session = session;
	}
	
	public ClientSession getSession() {
		return session;
	}
	
}
