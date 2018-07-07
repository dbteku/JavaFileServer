package com.dbteku.fileserver.tools;

import com.dbteku.fileserver.directories.ClientSessionDirectory;
import com.dbteku.fileserver.models.ClientSession;

public class Authorization {

	public static final String AUTH_HEADER = "authentication";
	
	public static boolean isLoggedIn(String sessionId) {
		boolean loggedIn = false;

		if(ClientSessionDirectory.getInstance().has(sessionId)) {
			ClientSession session = ClientSessionDirectory.getInstance().get(sessionId);
			long now = System.currentTimeMillis();
			if(session.getExpireTime() - now > 0) {
				renewSession(sessionId);
				loggedIn = true;
			}else {
				ClientSessionDirectory.getInstance().delete(sessionId);
			}
		}

		return loggedIn;
	}
	
	private static void renewSession(String id) {
		ClientSession session = ClientSessionDirectory.getInstance().get(id);
		if(session.isNull()) {
		}else {
			long now = System.currentTimeMillis();
			long expire = now + (1000 * 60 * 30);
			session.setExpireTime(expire);
		}
	}
	
}
