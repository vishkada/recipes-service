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
public class UsedIngredientsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1928009079844618462L;
	private Integer id;
	private Integer usedIngredientId;
	private String usedIngredientName;	
	
}
