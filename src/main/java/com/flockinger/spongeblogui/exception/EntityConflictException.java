package com.flockinger.spongeblogui.exception;

public class EntityConflictException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 376285314228555058L;

	public EntityConflictException(String message){
		super(message);
	}
}
