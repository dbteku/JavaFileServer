package com.dbteku.fileserver.api.services;

import com.dbteku.fileserver.models.NewUser;
import com.dbteku.fileserver.responses.HttpResponse;

public interface IAuthServiceV1 {

	HttpResponse isLoggedIn(String sessionId);
	HttpResponse login(NewUser newUser);
	HttpResponse logout(String sessionId);
	
}
