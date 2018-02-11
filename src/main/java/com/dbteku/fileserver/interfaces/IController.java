package com.dbteku.fileserver.interfaces;

public interface IController {

	default void start() {
		start(new String[0]);
	}
	
	void start(String[] args);
	
}
