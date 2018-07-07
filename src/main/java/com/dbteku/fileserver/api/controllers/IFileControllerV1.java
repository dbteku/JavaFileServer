package com.dbteku.fileserver.api.controllers;

import com.dbteku.fileserver.interfaces.IApiController;
import com.dbteku.fileserver.responses.HttpResponse;

import spark.Request;
import spark.Response;

public interface IFileControllerV1 extends IApiController{
	HttpResponse getFile(Request req, Response res);
	HttpResponse deleteFile(Request req, Response res);
	HttpResponse uploadFile(Request req, Response res);
}
