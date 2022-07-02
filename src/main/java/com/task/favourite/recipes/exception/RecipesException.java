package com.task.favourite.recipes.exception;

import org.springframework.beans.factory.annotation.Autowired;

public class RecipesException extends Exception {
	
	@Autowired
	ErrorMessage errorMessage;
	
    public RecipesException() {
        super();
    }

    public RecipesException(String message) {
        super(message);
    }

    public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
}
