package com.dbteku.fileserver.responses;

public class RawDataResponse extends HttpResponse{

	public RawDataResponse(Object obj) {
		super(true, false, "Ok", 200, obj);
	}
	
}
