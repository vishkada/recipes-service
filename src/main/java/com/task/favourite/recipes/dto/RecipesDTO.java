package com.task.favourite.recipes.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1390387638668911676L;
	private Long recipesId;
	private String recipesName;
	private String recipesType;
	private Integer noOfPerson;
	private String cookingInstruction;
	private Timestamp preparedAt;
	private Timestamp updatedAt;
	private Set<UsedIngredientsDTO> usedingredients = new HashSet<>();

}
