package com.task.favourite.recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.task.favourite.recipes.dto.UsersDTO;
import com.task.favourite.recipes.entity.Users;
import com.task.favourite.recipes.exception.UserException;
import com.task.favourite.recipes.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;

/**
 * {@link UserController}
 * 
 * User controller to handle the request for adding user

 * @author Vishwas_Kadam
 *
 */
@Log4j2
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/user")
	@Operation(
	        summary = "Add new user",
	        description = "Add new user",
	        tags = { "Users" },
	        responses = {
	            @ApiResponse(
	                description = "Success",
	                responseCode = "200",
	                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Users.class))
	            ),
	            @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
	            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
	        }
	    )
	public String addUser(@RequestBody UsersDTO usersDTO) throws UserException{
		log.info(" In addUser() of  UserController ");
		 userService.saveUser(usersDTO);
		 return "User added successfully";
	}
}
