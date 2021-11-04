package com.trungtamjava.rest;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trungtamjava.exception.JwtCustomException;
import com.trungtamjava.model.ResponseErrorDTO;

@RestControllerAdvice(basePackages = { "com.trungtamjava.rest" })
public class RestExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(RestExceptionController.class);

	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	@ResponseStatus(code = HttpStatus.CONFLICT)
	protected ResponseErrorDTO handleConflict(DataIntegrityViolationException ex) {
		return new ResponseErrorDTO(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase());
	}

	@ExceptionHandler(value = { AccessDeniedException.class, JwtCustomException.class })
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	protected ResponseErrorDTO handleUnauthorized(Exception ex) {
		return new ResponseErrorDTO(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class, IllegalArgumentException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	protected ResponseErrorDTO handleBadRequest(Exception ex) {
		logger.trace(ex.getMessage());
		return new ResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	@ExceptionHandler(value = { NoResultException.class, NullPointerException.class })
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	protected ResponseErrorDTO handleNotFound(Exception ex) {
		logger.trace(ex.getMessage());
		return new ResponseErrorDTO(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	protected ResponseErrorDTO handleServerError(Exception ex) {
		logger.debug(ex.getMessage());
		return new ResponseErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}
}