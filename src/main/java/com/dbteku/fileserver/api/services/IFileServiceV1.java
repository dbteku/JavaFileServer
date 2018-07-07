package com.dbteku.fileserver.api.services;

import com.dbteku.fileserver.responses.HttpResponse;

import spark.Request;
import spark.Response;

public interface IFileServiceV1 {

	HttpResponse getFile(String filePath, Response res); 
	HttpResponse deleteFile(String filePath);
	HttpResponse uploadFile(Request req);
	
}
