package com.dbteku.fileserver.interfaces;

public interface IDirectory<K, V> {

	boolean put(K key, V value);
	void delete(K key);
	boolean has(K key);
	void update(K key, V value);
	V get(K key);
	
}
