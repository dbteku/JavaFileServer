package com.dbteku.fileserver.services;
import static spark.Spark.port;
import static spark.Spark.stop;

import com.dbteku.fileserver.api.ApiV1;
import com.dbteku.fileserver.interfaces.IService;

public class HttpService implements IService{

	private int port;
	private boolean isRunning;
	
	public HttpService(int port) {	
		this.port = port;
	}

	@Override
	public void start() {
		port(this.port);
//		File file = new File("");
//		staticFiles.externalLocation(file.getAbsolutePath() + "/public");
		ApiV1 v1 = new ApiV1();
		v1.start();
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
