package com.task.favourite.recipes.service;

import java.util.List;

import com.task.favourite.recipes.dto.RecipesDTO;
import com.task.favourite.recipes.exception.RecipesException;

public interface RecipesService {

	public List<RecipesDTO> getRecipes()  throws RecipesException;
	
	public void saveRecipe(RecipesDTO recipesDTO) throws   RecipesException;
	
	public void deleteRecipe(Long RecipesId)  throws RecipesException;
	
	public void deleteRecipeByName(String RecipesName)  throws RecipesException;
	
	public void  updateRecipe(RecipesDTO recipesDTO) throws RecipesException ;

}
