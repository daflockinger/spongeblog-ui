package com.flockinger.spongeblogui.resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flockinger.spongeblogui.exception.ClientUnavailableException;
import com.flockinger.spongeblogui.exception.DtoValidationFailedException;
import com.flockinger.spongeblogui.exception.EntityConflictException;
import com.flockinger.spongeblogui.exception.EntityIsNotExistingException;
import com.flockinger.spongeblogui.resource.dto.Error;

import feign.Response;
import feign.Response.Body;
import feign.codec.ErrorDecoder;

public class ClientErrorHandler implements ErrorDecoder {

	private ObjectMapper mapper = new ObjectMapper();
	private static Logger logger = Logger.getLogger(ClientErrorHandler.class.getName());

	@Override
	public Exception decode(String methodKey, Response response) {

		if (response.status() == 404) {
			return new EntityIsNotExistingException(parseResponseBody(response.body()).getMessage());
		} else if (response.status() == 409) {
			return new EntityConflictException(parseResponseBody(response.body()).getMessage());
		} else if (response.status() == 400) {
			Error error = parseResponseBody(response.body());
			return new DtoValidationFailedException(error.getMessage(), error.getFields());
		}
		return new ClientUnavailableException(bodyToString(response.body()));
	}

	private String bodyToString(Body body) {
		String response = "";
		try {
			if (body != null) {
				response = IOUtils.toString(body.asInputStream(), StandardCharsets.UTF_8);
			}
		} catch (IOException e) {
			logger.error("Can't deserialize Error response: ", e);
		}
		return response;
	}

	private Error parseResponseBody(Body body) {
		Error error = new Error();
		try {
			if (body != null) {
				error = mapper.readValue(body.asInputStream(), Error.class);
			}
		} catch (IOException e) {
			logger.error("Can't deserialize Error response: ", e);
		}
		return error;
	}

}
