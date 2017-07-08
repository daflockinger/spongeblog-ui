package com.flockinger.spongeblogui.resource.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Error
 */
public class Error {

	private Integer code = null;
	private String message = null;
	private Map<String, String> fields = new HashMap<String, String>();

	/**
	 * Get code
	 * 
	 * @return code
	 **/
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * Get message
	 * 
	 * @return message
	 **/
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Invalid fields.
	 * 
	 * @return fields
	 **/
	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
}