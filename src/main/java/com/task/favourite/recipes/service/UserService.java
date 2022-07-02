package com.task.favourite.recipes.service;

import com.task.favourite.recipes.dto.UsersDTO;
import com.task.favourite.recipes.exception.UserException;

public interface UserService {
	
	public void saveUser(UsersDTO usersDTO) throws UserException;

}
