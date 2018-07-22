package com.dbteku.fileserver.api.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.dbteku.fileserver.responses.HttpResponse;
import com.dbteku.fileserver.responses.InternalErrorResponse;
import com.dbteku.fileserver.responses.JsonResponse;
import com.dbteku.fileserver.responses.NotFoundResponse;
import com.dbteku.fileserver.responses.OkResponse;
import com.dbteku.fileserver.responses.RawDataResponse;

import spark.Request;
import spark.Response;
import spark.utils.IOUtils;

public class FileServiceV1 implements IFileServiceV1{
	
	private static FileServiceV1 instance;
	private static final int DATA_PER_SECOND = 10000;
	
	private FileServiceV1() {
	}
	
	public static FileServiceV1 getInstance() {
		if(instance == null) {
			instance = new FileServiceV1();
		}
		return instance;
	}
	

	@Override
	public HttpResponse getFiles(String sessionId, String rootPath, int lastIndex, int numberOfRecords){
		File file = new File("files/");
		Object obj = file.list();
		return new JsonResponse(obj);
	}
	
	@Override
	public HttpResponse getFile(String sessionId, String filePath, Response res) {
		HttpResponse response = new NotFoundResponse();
		File potential = new File("files/" + filePath);
		if(potential.exists()) {
			FileInputStream inputstream = null;
			try {
				inputstream = new FileInputStream("files/" + filePath);
			} catch (FileNotFoundException e) {
				response = new InternalErrorResponse();
				e.printStackTrace();
			}
	        HttpServletResponse raw = res.raw();
	        res.header("Content-Disposition", "attachment; filename=" + filePath);
	        res.type("application/force-download");
	        try {
	        	if(inputstream != null) {
			    	while(inputstream.available() > 0) {
		    			byte[] data = new byte[0];
			    		if(inputstream.available() >= DATA_PER_SECOND) {
			    			data = new byte[DATA_PER_SECOND];
			    		}else {
			    			data = new byte[inputstream.available()];
			    		}
		    			inputstream.read(data);
		    			raw.getOutputStream().write(data);
			            raw.getOutputStream().flush();
			    	}
		            raw.getOutputStream().close();
		            inputstream.close();	
	        	}
	        } catch (Exception e) {
				response = new InternalErrorResponse();
	            e.printStackTrace();
	        }
	       response = new RawDataResponse(raw);
		}
        return response;
	}

	@Override
	public HttpResponse deleteFile(String sessionId, String filePath) {
		HttpResponse response = new NotFoundResponse();
		File file = new File("files/" + filePath);
		if(file.exists()) {
			file.delete();
			response = new OkResponse();
		}
		return response;
	}

	@Override
	public HttpResponse uploadFile(String sessionId, Request req) {
		HttpResponse response = new OkResponse();
		BigInteger integer = new BigInteger("100000000000");
		req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("temp", integer.longValue(), integer.longValue(), DATA_PER_SECOND));
		String fileName = getFileName(req);
		try (InputStream is = new BufferedInputStream(req.raw().getPart("file").getInputStream())) {
			FileOutputStream stream = new FileOutputStream("files/" + req.raw().getPart("file").getSubmittedFileName());
			IOUtils.copyLarge(is, stream);
			stream.flush();
			stream.close();
		}catch(IOException e) {
			File toDelete = new File("files/" + fileName);
			if(toDelete.exists()) {
				toDelete.delete();
			}
			e.printStackTrace();
		} catch (ServletException e) {
			response = new InternalErrorResponse();
			e.printStackTrace();
			
		}
		return response;
	}
	
	private String getFileName(Request req) {
		String fileName =  "";
		
		try {
			fileName = req.raw().getPart("file").getSubmittedFileName();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
		return fileName;
	}

}
