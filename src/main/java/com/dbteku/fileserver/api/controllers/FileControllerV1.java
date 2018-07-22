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
			return gson.toJson(listFiles(req, res));
		});

		get("/v1/files/download", (req, res)->{
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

		delete("/v1/files", (req, res)->{
			return gson.toJson(deleteFile(req, res));
		});
	}

	@Override
	public HttpResponse listFiles(Request req, Response res) {
		HttpResponse response = new UnAuthorizedResponse();
		if(Authorization.isLoggedIn(req.headers(Authorization.AUTH_HEADER))) {
			String activeDirectory = "/";
			if(req.queryMap().hasKey("activeDirectory")) {
				activeDirectory = req.queryMap().get("activeDirectory").value();
			}
			response = service.listFiles(req.headers(Authorization.AUTH_HEADER), activeDirectory, 0, 0);	
		}
		res.status(response.getStatusCode());
		return response;
	}

	@Override
	public HttpResponse getFile(Request req, Response res) {
		HttpResponse response = new UnAuthorizedResponse();
		if(Authorization.isLoggedIn(req.headers(Authorization.AUTH_HEADER))) {
			if(req.queryMap().hasKey("location")) {
				String location = req.queryMap().get("location").value();
				response = service.getFile(req.headers(Authorization.AUTH_HEADER), location, res);
			}
		}
		res.status(response.getStatusCode());
		return response;
	}

	@Override
	public HttpResponse deleteFile(Request req, Response res) {
		HttpResponse response = new UnAuthorizedResponse();
		if(Authorization.isLoggedIn(req.headers(Authorization.AUTH_HEADER))) {
			if(req.queryMap().hasKey("location")) {
				String location = req.queryMap().get("location").value();
				response = service.deleteFile(req.headers(Authorization.AUTH_HEADER), location);
			}
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
