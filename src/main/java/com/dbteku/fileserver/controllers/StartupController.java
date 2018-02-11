package com.dbteku.fileserver.controllers;

import com.dbteku.fileserver.interfaces.IController;
import com.dbteku.fileserver.interfaces.IService;
import com.dbteku.fileserver.services.HttpService;

public class StartupController implements IController{

	private final int PORT = 80;

	public StartupController() {
	}

	@Override
	public void start(String[] args) {
//		NewUser user = new NewUser("Test", "test");
//		SaltGenerator generator = new SaltGenerator();
//		String salt = generator.generate();
//		String saltedPassword = user.getPassword() + salt;
//		Sha256Encryption encryption = new Sha256Encryption();
//		String encrypedPassword = encryption.encrypt(saltedPassword);
//		ProtectedUser protectedUser = new ProtectedUser(user.getUsername(), encrypedPassword, salt);
//		ClientDirectory.getInstance().put(protectedUser.getUsername(), protectedUser);
		IService service = new HttpService(PORT);
		service.start();
	}

}
