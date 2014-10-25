package com.tracker.db.simplehbase.type.handler;

import org.apache.hadoop.hbase.util.Bytes;

import com.tracker.db.simplehbase.type.TypeHandler;

/**
 * @author jason.hua
 * */
public class StringHandler implements TypeHandler {

	@Override
	public Object stringToObject(byte[] bytes) {
		return Bytes.toString(bytes);
	}

	@Override
	public byte[] toBytes(Object value) {
		 return Bytes.toBytes((String) value);
	}

	@Override
	public Object toObject(byte[] bytes) {
		 return Bytes.toString(bytes);
	}

	@Override
	public byte[] stringToBytes(Object value) {
		return Bytes.toBytes(String.valueOf(value));
	}

}
