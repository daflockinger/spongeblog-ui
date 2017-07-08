package com.flockinger.spongeblogui.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import com.flockinger.spongeblogui.exception.ClientUnavailableException;
import com.flockinger.spongeblogui.exception.DtoValidationFailedException;
import com.flockinger.spongeblogui.exception.EntityIsNotExistingException;
import com.flockinger.spongeblogui.exception.InvalidRequestUrlException;
import com.flockinger.spongeblogui.resource.dto.Error;

import feign.FeignException;

@ControllerAdvice
public class ErrorPageHandler {
	
	private final static String ERROR_MESSAGE_NAME = "message";

	@ExceptionHandler(value = { ClientUnavailableException.class , FeignException.class})
	protected ModelAndView handleNotFound(Exception ex, WebRequest request) {
		ModelAndView view = new ModelAndView("/502");

		view.addObject(ERROR_MESSAGE_NAME, "502 " + ex.getMessage());
		
		return view;
	}
	
	@ExceptionHandler(value = {MethodArgumentTypeMismatchException.class}) 
	protected String handleWeirdUrls(Exception ex, WebRequest request) {
		return "redirect:/";
	}

	@ExceptionHandler(value = { EntityIsNotExistingException.class, InvalidRequestUrlException.class})
	protected ModelAndView handleConflict(Exception ex, WebRequest request) {
		ModelAndView view = new ModelAndView("/404");
		
		view.addObject(ERROR_MESSAGE_NAME, "404 " + ex.getMessage());
		
		return view;
	}
	
	@ExceptionHandler(value = { DtoValidationFailedException.class })
	protected ResponseEntity<Error> handleValidationFailed(DtoValidationFailedException ex, WebRequest request) {
		Error error = new Error();
		error.setMessage(ex.getMessage());
		error.setFields(ex.getFields());
		
		return new ResponseEntity<Error>(error,HttpStatus.BAD_REQUEST);
	}
}
