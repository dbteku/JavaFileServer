package com.dbteku.fileserver.api.services;

import com.dbteku.fileserver.responses.HttpResponse;

import spark.Request;
import spark.Response;

public interface IFileServiceV1 {

	HttpResponse listFiles(String sessionId, String rootPath, int lastIndex, int numberOfRecords);
	HttpResponse getFile(String sessionId, String fileName, Response res); 
	HttpResponse deleteFile(String sessionId, String fileName);
	HttpResponse uploadFile(String sessionId, Request req);
	
}
