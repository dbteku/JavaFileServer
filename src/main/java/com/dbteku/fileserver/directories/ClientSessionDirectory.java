package com.dbteku.fileserver.directories;

import com.dbteku.fileserver.interfaces.IDirectory;
import com.dbteku.fileserver.models.ClientSession;

public class ClientSessionDirectory implements IDirectory<String, ClientSession> {

	@Override
	public boolean put(String sessionId, ClientSession value) {
		return false;
	}

	@Override
	public void delete(String sessionId) {
		
	}

	@Override
	public boolean has(String sessionId) {
		return false;
	}

	@Override
	public void update(String sessionId, ClientSession value) {

	}

	@Override
	public ClientSession get(String sessionId) {
		return null;
	}

	
}
