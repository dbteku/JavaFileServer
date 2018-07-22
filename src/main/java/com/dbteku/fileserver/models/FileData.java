package com.dbteku.fileserver.models;

public class FileData {

	private String fileName;
	private boolean isDirectory;
	
	public FileData(String fileName, boolean isDirectory) {
		this.fileName = fileName;
		this.isDirectory = isDirectory;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public boolean isDirectory() {
		return isDirectory;
	}
	
}
