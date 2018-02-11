package com.dbteku.fileserver.responses;

public class HttpResponse {

	private final boolean SUCCESS;
	private final boolean ERROR;
	private final String MESSAGE;
	
	public HttpResponse(boolean success, boolean error, String message) {
		this.SUCCESS = success;
		this.ERROR = error;
		this.MESSAGE = message;
	}
	
	public boolean isSuccess() {
		return SUCCESS;
	}
	
	public boolean isError() {
		return ERROR;
	}
	
	public String getMessage() {
		return MESSAGE;
	}
	
}
