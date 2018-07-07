package com.dbteku.fileserver.responses;

public class JsonResponse extends HttpResponse{

	private final Object DATA;
	
	public JsonResponse(Object data) {
		super(true, false, "Ok", 200);
		this.DATA = data;
	}
	
	
	public Object getData() {
		return DATA;
	}
}
