package com.tracker.db.simplehbase.type.handler;

import org.apache.hadoop.hbase.util.Bytes;

import com.tracker.db.simplehbase.type.TypeHandler;
/**
 * @author jason.hua
 * */
public class IntegerHandler implements TypeHandler {
	
	@Override
	public Object stringToObject(byte[] bytes) {
		return Integer.valueOf(Bytes.toString(bytes));
	}

	@Override
	public byte[] toBytes(Object value) {
		return Bytes.toBytes((Integer) value);
	}

	@Override
	public Object toObject(byte[] bytes) {
		return Bytes.toInt(bytes);
	}
	
	@Override
	public byte[] stringToBytes(Object value) {
		return Bytes.toBytes(String.valueOf(value));
	}

}
