package com.dbteku.fileserver.api.controllers;

import com.dbteku.fileserver.interfaces.IApiController;
import com.dbteku.fileserver.responses.HttpResponse;

import spark.Request;
import spark.Response;

public interface IAuthControllerV1 extends IApiController{

	HttpResponse login(Request req, Response res);
	HttpResponse logout(Request req, Response res);
	HttpResponse isLoggedIn(Request req, Response res);
	
}
