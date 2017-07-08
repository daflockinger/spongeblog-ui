package com.flockinger.spongeblogui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ClientUnavailableException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7009815319503756953L;

	public ClientUnavailableException(String message) {
		super(message);
	}
}
