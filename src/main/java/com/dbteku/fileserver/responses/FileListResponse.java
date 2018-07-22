package com.dbteku.fileserver.responses;

import com.dbteku.fileserver.models.FileData;

public class FileListResponse extends HttpResponse{

	private final String ACTIVE_DIRECTORY;
	private final FileData[] DATA;
	
	public FileListResponse(String activeDirectory, FileData[] data) {
		super(true, false, "Ok", 200);
		this.ACTIVE_DIRECTORY = activeDirectory;
		this.DATA = data;
	}
	
	public String getActiveDirectory() {
		return ACTIVE_DIRECTORY;
	}
	
	public FileData[] getData() {
		return DATA;
	}
	
}
