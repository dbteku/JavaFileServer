package com.dbteku.fileserver.interfaces;

public interface INullable {

	default boolean isNull() {
		return false;
	}
	
}
