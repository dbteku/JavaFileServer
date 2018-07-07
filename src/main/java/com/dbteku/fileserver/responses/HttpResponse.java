package com.dbteku.fileserver.responses;

public class HttpResponse {

	private final boolean SUCCESS;
	private final boolean ERROR;
	private final String MESSAGE;
	private final int STATUS_CODE;
	private final Object RAW_DATA;
	
	public HttpResponse(boolean success, boolean error, String message, int statusCode, Object rawData) {
		this.SUCCESS = success;
		this.ERROR = error;
		this.MESSAGE = message;
		this.STATUS_CODE = statusCode;
		this.RAW_DATA = rawData;
	}
	
	public HttpResponse(boolean success, boolean error, String message, int statusCode) {
		this(success, error, message, statusCode, null);
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
	
	public int getStatusCode() {
		return STATUS_CODE;
	}
	
	public Object getRawData() {
		return RAW_DATA;
	}
	
}
