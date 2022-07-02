package com.task.favourite.recipes.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * {@link RestResponseEntityExceptionHandler}
 * 
 * RestResponseEntityExceptionHandler interface is to handle the custom exception.
 * 
 * @author Vishwas_Kadam
 */

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(RecipesException.class)
	public ResponseEntity<ErrorMessage> recipesNotFoundException(RecipesException exception,
												WebRequest request) {
		return ResponseEntity.status(exception.errorMessage.getStatus()).body(exception.errorMessage);
	}

	@ExceptionHandler(IngredientException.class)
	public ResponseEntity<ErrorMessage> ingredientNotFoundException(IngredientException exception,
												WebRequest request) {
		return ResponseEntity.status(exception.errorMessage.getStatus()).body(exception.errorMessage);
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorMessage> userFoundException(UserException exception,
												WebRequest request) {
		return ResponseEntity.status(exception.errorMessage.getStatus()).body(exception.errorMessage);
	}
}
