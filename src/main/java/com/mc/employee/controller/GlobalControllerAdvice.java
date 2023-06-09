package com.mc.employee.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.view.ErrorMessage;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(value = EmployeeNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(EmployeeNotFoundException exception, WebRequest request) {
		log.warn(exception.getMessage());
		ErrorMessage error = ErrorMessage.builder().error(exception.getMessage()).build();
		return new ResponseEntity<	>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> onConstraintValidationException(ConstraintViolationException exception) {
		log.warn("onConstraintValidationException. {}", exception.getMessage());
		
		ErrorMessage error = ErrorMessage.builder().error(exception.getMessage()).build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ErrorMessage>> handleValidationErrors(MethodArgumentNotValidException ex) {
		log.warn("Validation error occourred. {}, {}", ex.getMessage(), ex.getParameter());
		
		List<ErrorMessage> errors = ex.getBindingResult().getFieldErrors().stream().map(e -> new ErrorMessage(e.getDefaultMessage())).collect(Collectors.toList());
		errors.addAll(ex.getBindingResult().getGlobalErrors().stream().map(e -> new ErrorMessage(e.getDefaultMessage())).toList());

		return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> onException(Exception exception) {
		log.error("Generic Exception.", exception);
		
		ErrorMessage error = ErrorMessage.builder().error(exception.getMessage()).build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
}
