package com.dbteku.fileserver.directories;

import java.util.HashMap;
import java.util.Map;

import com.dbteku.fileserver.interfaces.IDirectory;
import com.dbteku.fileserver.models.NullProtectedUser;
import com.dbteku.fileserver.models.ProtectedUser;

public class RamUserDirectory implements IDirectory<String, ProtectedUser>{

	private static RamUserDirectory instance;
	private Map<String, ProtectedUser> users;
	
	private RamUserDirectory() {
		users = new HashMap<>();
	}
	
	public static RamUserDirectory getInstance() {
		if(instance == null) {
			instance = new RamUserDirectory();
		}
		
		return instance;
	}

	@Override
	public boolean put(String username, ProtectedUser user) {
		boolean put = false;
		if(!has(username)) {
			users.put(username, user);
			put = has(username);
		}
		return put;
	}

	@Override
	public void delete(String username) {
		users.remove(username);
	}

	@Override
	public boolean has(String username) {
		return users.containsKey(username);
	}

	@Override
	public void update(String username, ProtectedUser user) {
		if(has(username)) {
			users.replace(username, user);
		}
	}

	@Override
	public ProtectedUser get(String username) {
		ProtectedUser user = new NullProtectedUser();
		if(has(username)) {
			user = users.get(username);
		}
		return user;
	}
	
}
