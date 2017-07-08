package com.flockinger.spongeblogui.exception;

import java.util.Map;

public class DtoValidationFailedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9110322794181495490L;
	
	private Map<String,String> fields;

	public DtoValidationFailedException(String message, Map<String,String> fields){
		super(message);
		this.fields = fields;
	}

	public Map<String,String> getFields() {
		return fields;
	}
}
