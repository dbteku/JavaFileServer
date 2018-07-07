package com.dbteku.fileserver.api.controllers;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import java.io.File;

import com.dbteku.fileserver.api.services.IFileServiceV1;
import com.dbteku.fileserver.responses.HttpResponse;
import com.dbteku.fileserver.responses.UnAuthorizedResponse;
import com.dbteku.fileserver.tools.Authorization;
import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class FileControllerV1 implements IFileControllerV1{

	private IFileServiceV1 service;
	private Gson gson;

	public FileControllerV1(IFileServiceV1 service) {
		this.service = service;
		gson = new Gson();
	}

	@Override
	public void start() {
		
		File file = new File("files");
		if(!file.isDirectory()) {
			file.mkdir();
		}
		
		get("/v1/files", (req, res)->{
			return gson.toJson(getFiles(req, res));
		});

		get("/v1/files/:file", (req, res)->{
			HttpResponse response = getFile(req, res);
			Object obj = response;
			if(response.getRawData() == null) {
				obj = gson.toJson(response);
			}
			return obj;
		});

		post("/v1/files", (req, res)->{
			return gson.toJson(uploadFile(req, res));
		});

		delete("/v1/files/:file", (req, res)->{
			return gson.toJson(deleteFile(req, res));
		});
	}
	
	@Override
	public HttpResponse getFiles(Request req, Response res) {
		HttpResponse response = new UnAuthorizedResponse();
		if(Authorization.isLoggedIn(req.headers(Authorization.AUTH_HEADER))) {
			response = service.getFiles(req.headers(Authorization.AUTH_HEADER));	
		}
		res.status(response.getStatusCode());
		return response;
	}

	@Override
	public HttpResponse getFile(Request req, Response res) {
		HttpResponse response = new UnAuthorizedResponse();
		if(Authorization.isLoggedIn(req.headers(Authorization.AUTH_HEADER))) {
			response = service.getFile(req.headers(Authorization.AUTH_HEADER), req.params(":file"), res);	
		}
		res.status(response.getStatusCode());
		return response;
	}

	@Override
	public HttpResponse deleteFile(Request req, Response res) {
		HttpResponse response = new UnAuthorizedResponse();
		if(Authorization.isLoggedIn(req.headers(Authorization.AUTH_HEADER))) {
			response = service.deleteFile(req.headers(Authorization.AUTH_HEADER), req.params(":file"));
		}
		res.status(response.getStatusCode());
		return response;
	}

	@Override
	public HttpResponse uploadFile(Request req, Response res) {
		HttpResponse response = new UnAuthorizedResponse();
		if(Authorization.isLoggedIn(req.headers(Authorization.AUTH_HEADER))) {
			response = service.uploadFile(req.headers(Authorization.AUTH_HEADER), req);
		}
		res.status(response.getStatusCode());
		return response;
	}

}
