package com.dbteku.fileserver.directories;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.dbteku.fileserver.interfaces.IDirectory;
import com.dbteku.fileserver.models.NullProtectedUser;
import com.dbteku.fileserver.models.ProtectedUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientDirectory implements IDirectory<String, ProtectedUser>{

	private static ClientDirectory instance;
	private final String FOLDER_NAME;
	private final String EXTENSION;
	private Gson gson;
	
	private ClientDirectory() {
		this.FOLDER_NAME = "users/";
		this.EXTENSION = ".json";
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		init();
	}
	
	public static ClientDirectory getInstance() {
		if(instance == null) {
			instance = new ClientDirectory();
		}
		return instance;
	}
	
	private void init() {
		File file = new File(FOLDER_NAME);
		if(file.exists() && file.isDirectory()) {
		}else {
			file.mkdir();
		}
	}

	@Override
	public boolean put(String key, ProtectedUser value) {
		boolean added = false;
		if(!has(key)) {
			try {
				writeFile(FOLDER_NAME + key + EXTENSION, gson.toJson(value));
				added = has(key);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return added;
	}

	@Override
	public void delete(String key) {
		if(has(key)) {
			File file = new File(FOLDER_NAME + key + EXTENSION);
			file.delete();
		}
	}

	@Override
	public boolean has(String key) {
		return new File(FOLDER_NAME + key + EXTENSION).exists();
	}

	@Override
	public void update(String key, ProtectedUser value) {
		if(has(key)) {
			try {
				writeFile(FOLDER_NAME + key + EXTENSION, gson.toJson(value));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ProtectedUser get(String key) {
		ProtectedUser user = new NullProtectedUser();
		if(has(key)) {
			try {
				String json = readFileToString(FOLDER_NAME + key + EXTENSION);
				user = gson.fromJson(json, ProtectedUser.class);
				if(user == null) {
					user = new NullProtectedUser();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	private void writeFile(String location, String json) throws IOException{
		FileOutputStream output = new FileOutputStream(location);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output));
		writer.print(json);
		writer.flush();
		writer.close();
	}
	
	private String readFileToString(String location) throws IOException{
		StringBuilder builder = new StringBuilder();
		DataInputStream inputStream = new DataInputStream(new FileInputStream(location));
		while(inputStream.available() > 0) {
			byte[] toRead = new byte[inputStream.available()];
			inputStream.read(toRead);
			builder.append(new String(toRead));
		}
		inputStream.close();
		return builder.toString();
	}
	
}
