package com.task.favourite.recipes.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8348402523111521387L;
	private Long userId;
	private String userName;
	private String password;
	private String email;
	private char active;
	private Set<UserRoleDTO> roles;

}
