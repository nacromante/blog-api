package com.framework.blogapi.exceptions;

public class DataConversionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status;


	public DataConversionException() {
		super();
	}
	public DataConversionException(int status) {
		super();
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
