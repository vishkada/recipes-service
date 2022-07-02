package com.task.favourite.recipes.exception;

import org.springframework.beans.factory.annotation.Autowired;

public class IngredientException extends Exception{
	
	@Autowired
	ErrorMessage errorMessage;
	 
	public IngredientException() {
        super();
    }

    public IngredientException(String message) {
        super(message);
    }
    public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
}
