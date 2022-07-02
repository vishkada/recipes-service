package com.task.favourite.recipes.service;

import java.util.List;

import com.task.favourite.recipes.dto.IngredientDTO;
import com.task.favourite.recipes.exception.IngredientException;


public interface IngredientService {
	
	public List<IngredientDTO> fetchIngredients() throws IngredientException;
	
	public IngredientDTO saveIngredient(IngredientDTO ingredientDTO)  throws IngredientException;
	
	public void deleteIngredient(Long ingredientId) throws IngredientException;
	
	public void deleteIngredientByName(String ingredientName) throws IngredientException;
}
