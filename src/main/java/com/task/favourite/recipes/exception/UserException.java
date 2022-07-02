package com.task.favourite.recipes.exception;

import org.springframework.beans.factory.annotation.Autowired;

public class UserException extends Exception {

	@Autowired
	ErrorMessage errorMessage;
	 
	public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }
    
    public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
}
