package com.dbteku.fileserver.main;

import com.dbteku.fileserver.controllers.StartupController;
import com.dbteku.fileserver.interfaces.IController;

public class FileServer {
	public static void main(String[] args) {
		IController controller = new StartupController();
		controller.start();
	}
}
