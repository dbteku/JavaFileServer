package com.dbteku.fileserver.services;
import static spark.Spark.port;
import static spark.Spark.stop;

import com.dbteku.fileserver.api.controllers.AuthControllerV1;
import com.dbteku.fileserver.api.controllers.FileControllerV1;
import com.dbteku.fileserver.api.controllers.IAuthControllerV1;
import com.dbteku.fileserver.api.controllers.IFileControllerV1;
import com.dbteku.fileserver.api.services.AuthServiceV1;
import com.dbteku.fileserver.api.services.FileServiceV1;
import com.dbteku.fileserver.interfaces.IService;
import com.dbteku.fileserver.tools.CorsUtility;

public class HttpService implements IService{

	private int port;
	private boolean isRunning;
	
	public HttpService(int port) {	
		this.port = port;
	}

	@Override
	public void start() {
		port(this.port);
		CorsUtility.enableCors();
		IFileControllerV1 fileController = new FileControllerV1(FileServiceV1.getInstance());
		fileController.start();
		IAuthControllerV1 authController = new AuthControllerV1(new AuthServiceV1());
		authController.start();
		isRunning = true;
	}

	@Override
	public void shutdown() {
		stop();
		isRunning = false;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}
	
}
