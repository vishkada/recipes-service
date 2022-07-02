package com.task.favourite.recipes.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1121062054207174130L;
	private Long roleId;
	private String role;
	
}
