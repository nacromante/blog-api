package com.framework.blogapi.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5344629881450729571L;

	public NotFoundException(String message) {
		super(message);
	}

}
