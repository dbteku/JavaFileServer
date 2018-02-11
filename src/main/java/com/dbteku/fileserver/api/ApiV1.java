package com.dbteku.fileserver.api;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import com.dbteku.fileserver.directories.ClientDirectory;
import com.dbteku.fileserver.directories.ClientSessionDirectory;
import com.dbteku.fileserver.interfaces.IService;
import com.google.gson.Gson;

import spark.Response;

public class ApiV1 implements IService{

	private static final String CONTENT_TYPE = "Content-Type";
	private static final String APPLICATION_JSON = "application/json";
	private static final String AUTH_HEADER = "authenciation";
	private ClientSessionDirectory sessions;
	private ClientDirectory clients;
	private Gson gson;
	
	public ApiV1() {
		this.sessions = ClientSessionDirectory.getInstance();
		this.clients = ClientDirectory.getInstance();
		this.gson = new Gson();
	}
	
	@Override
	public void start() {
		
		post("/v1/auth/user", (req, res)->{
			String header = req.headers(AUTH_HEADER);
			setJsonHeaders(res);
			return null;
		});
		
		delete("/v1/auth/user", (req, res)->{
			setJsonHeaders(res);
			return null;
		});
		
		post("/v1/auth/login", (req, res)->{
			setJsonHeaders(res);
			return null;
		});
		post("/v1/auth/logout", (req, res)->{
			setJsonHeaders(res);
			return null;
		});
		
		get("/v1/files", (req, res)->{
			return null;
		});
		
		get("/v1/files/:file", (req, res)->{
			return null;
		});
		
		put("/v1/files/:file", (req, res)->{
			return null;
		});
		
		delete("/v1/files/:file", (req, res)->{
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
