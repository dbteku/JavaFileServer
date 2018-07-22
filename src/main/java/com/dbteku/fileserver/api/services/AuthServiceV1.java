package com.dbteku.fileserver.api.services;

import java.util.UUID;

import com.dbteku.fileserver.directories.ClientDirectory;
import com.dbteku.fileserver.directories.ClientSessionDirectory;
import com.dbteku.fileserver.models.ClientSession;
import com.dbteku.fileserver.models.NewUser;
import com.dbteku.fileserver.models.ProtectedUser;
import com.dbteku.fileserver.responses.HttpResponse;
import com.dbteku.fileserver.responses.InvalidJsonResponse;
import com.dbteku.fileserver.responses.InvalidUsernameOrPassword;
import com.dbteku.fileserver.responses.LoggedInResponse;
import com.dbteku.fileserver.responses.NotFoundResponse;
import com.dbteku.fileserver.responses.OkResponse;
import com.dbteku.fileserver.tools.Sha256Encryption;

public class AuthServiceV1 implements IAuthServiceV1{

	private static final long ONE_SECOND_IN_MILLIS = 1000;
	private ClientSessionDirectory sessions;
	private ClientDirectory clients;
	
	public AuthServiceV1() {
		this.sessions = ClientSessionDirectory.getInstance();
		this.clients = ClientDirectory.getInstance();
	}
	
	@Override
	public HttpResponse isLoggedIn(String sessionId) {
		HttpResponse response = new NotFoundResponse();
		
		if(sessions.has(sessionId)) {
			response = new LoggedInResponse(sessions.get(sessionId));
		}
		
		return response;
	}

	@Override
	public HttpResponse login(NewUser newUser) {
		HttpResponse response = new NotFoundResponse();
		if(newUser == null) {
			response = new InvalidJsonResponse();
		}else {
			ProtectedUser protectedUser = clients.get(newUser.getUsername());
			if(protectedUser.isNull()) {
				response = new InvalidUsernameOrPassword();
			}else {
				String potentialSaltedPassword = newUser.getPassword() + protectedUser.getSaltCode();
				Sha256Encryption encryption = new Sha256Encryption();
				String potentialPassword = encryption.encrypt(potentialSaltedPassword);
				if(potentialPassword.equals(protectedUser.getHashedPassword())) {
					String sessionId = newUser.getUsername() + "-" + UUID.randomUUID().toString();
					while(sessions.has(sessionId)) {
						sessionId = UUID.randomUUID().toString();
					}
					long now = System.currentTimeMillis();
					long expire = now + (ONE_SECOND_IN_MILLIS * 60 * 30);
					ClientSession session = new ClientSession(sessionId, protectedUser.getUsername(), expire);
					sessions.put(session.getId(), session);
					response = new LoggedInResponse(session);
				}else {
					response = new InvalidUsernameOrPassword();
				}
			}
		}

		return response;
	}

	@Override
	public HttpResponse logout(String sessionId) {
		sessions.delete(sessionId);
		return new OkResponse();
	}

}
