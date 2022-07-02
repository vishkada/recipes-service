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
public class UserRoleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8149132702393328923L;
	private Integer id;
	private Integer userRoleId;
	private String userRoleName;

}
