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
		IService service = new HttpService(PORT);
		service.start();
	}
	
}
