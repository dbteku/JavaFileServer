package com.dbteku.fileserver.api;

import static spark.Spark.post;
import com.dbteku.fileserver.interfaces.IService;

import spark.Response;

public class ApiV1 implements IService{

	private static final String CONTENT_TYPE = "Content-Type";
	private static final String APPLICATION_JSON = "application/json";
	
	@Override
	public void start() {
		
		post("/v1/users", (req, res)->{
			setJsonHeaders(res);
			return null;
		});
		
	}
	
	private void setJsonHeaders(Response res) {
		res.header(CONTENT_TYPE, APPLICATION_JSON);
	}

	@Override
	public void shutdown() {

	}

	@Override
	public boolean isRunning() {
		return true;
	}

}
