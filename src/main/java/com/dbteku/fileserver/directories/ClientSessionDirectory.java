package com.dbteku.fileserver.directories;

import java.util.HashMap;
import java.util.Map;

import com.dbteku.fileserver.interfaces.IDirectory;
import com.dbteku.fileserver.models.ClientSession;
import com.dbteku.fileserver.models.NullClientSession;

public class ClientSessionDirectory implements IDirectory<String, ClientSession> {

	private static ClientSessionDirectory instance;
	private Map<String, ClientSession> sessions;
	
	private ClientSessionDirectory() {
		this.sessions = new HashMap<>();
	}
	
	public static ClientSessionDirectory getInstance() {
		if(instance == null) {
			instance = new ClientSessionDirectory();
		}
		
		return instance;
	}
	
	@Override
	public boolean put(String sessionId, ClientSession value) {
		return false;
	}

	@Override
	public void delete(String sessionId) {
		sessions.remove(sessionId);
	}

	@Override
	public boolean has(String sessionId) {
		return sessions.containsKey(sessionId);
	}

	@Override
	public void update(String sessionId, ClientSession value) {
		sessions.replace(sessionId, value);
	}

	@Override
	public ClientSession get(String sessionId) {
		ClientSession session = new NullClientSession();
		
		if(has(sessionId)) {
			session = sessions.get(sessionId);
		}
		
		return session;
	}

	
}
