package com.task.favourite.recipes.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8113377208952638171L;
	private Long ingredientId;
	private String ingredientName;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
}
