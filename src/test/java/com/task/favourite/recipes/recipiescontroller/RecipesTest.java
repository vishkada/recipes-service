package com.task.favourite.recipes.recipiescontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.task.favourite.recipes.controller.RecipesController;
import com.task.favourite.recipes.dto.RecipesDTO;
import com.task.favourite.recipes.dto.UsedIngredientsDTO;
import com.task.favourite.recipes.entity.Recipes;
import com.task.favourite.recipes.entity.UsedIngredients;
import com.task.favourite.recipes.exception.RecipesException;
import com.task.favourite.recipes.repository.RecipesRepository;

@SpringBootTest
public class RecipesTest {
	
	@MockBean
	private RecipesRepository  recipesRepository;
	
	@Autowired
	private RecipesController recipesController;
	
	/**
	 * Adding a recipes which is not available in the DB 
	 * We are mocking the save Method and  findByRecipesName method 
	 * @throws Exception
	 */
	@Test
	public void addRecipesTestWithPositiveScenario() throws Exception{
		Mockito.when(recipesRepository.findByRecipesName(Mockito.anyString())).thenReturn( Optional.of(new Recipes()));
		Mockito.when(recipesRepository.save(Mockito.any(Recipes.class))).thenReturn(getRecipe());
		try {
			String result = recipesController.addRecipe(getRecipeDTO());
			assertEquals( "Recipe is added successfully", result);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Adding a recipes which is  available in the DB 
	 * We are mocking the save Method and  findByRecipesName method 
	 * @throws Exception
	 */
	@Test
	public void addRecipesTestWithNegativeScenario() throws Exception{
		Mockito.when(recipesRepository.findByRecipesName(Mockito.anyString())).thenReturn( Optional.of(getRecipe()));
		Mockito.when(recipesRepository.save(Mockito.any(Recipes.class))).thenReturn(getRecipe());
		try {
			String result = recipesController.addRecipe(getRecipeDTO());
			assertEquals( "Con not add, Recipes is already exist", result);
		} catch (Exception e) {
			
		}
		
	}
	
	/**
	 * fetchRecipes 
	 * mocking the findAll() Method with list of recipes 
	 * @throws Exception
	 */
	@Test
	public void fetchRecipesWithpositiveScenario() throws Exception{
		
		List<Recipes> recipesList = new ArrayList<>();
		recipesList.add(getRecipe());
		Mockito.when(recipesRepository.findAll()).thenReturn(recipesList);
		ResponseEntity<List<RecipesDTO>> result = recipesController.getRecipes();
		assertNotNull(result.getBody());

	}
	
	/**
	 * fetchRecipes 
	 * mocking the findAll() Method with null 
	 * @throws Exception
	 */
	@Test
	public void fetchRecipesWithNegativeScenario() throws Exception{
		List<Recipes> recipesList = new ArrayList<>();
		Mockito.when(recipesRepository.findAll()).thenReturn(recipesList);
		try {
			ResponseEntity<List<RecipesDTO>> result = recipesController.getRecipes();
			assertEquals(0, result.getBody().size());
		} catch (Exception e) {
			
		}
		
	}
	
	/**
	 * deleteRecipe 
	 * mocking the existsById() Method with true 
	 * @throws Exception
	 */
	@Test
	public void deleteRecipesWithPositiveScenario() throws RecipesException{
		
		Mockito.when(recipesRepository.existsById(Mockito.anyLong())).thenReturn(true);
		Mockito.doNothing().when(recipesRepository).deleteById(Mockito.anyLong());
		String result = recipesController.deleteRecipe(10L);
		assertEquals( "Recipe is deleted successfully", result);
	}
	
	/**
	 * deleteRecipe 
	 * mocking the existsById() Method with false 
	 * @throws Exception
	 */
	@Test
	public void deleteRecipesWithNegativeScenario() throws RecipesException{
		
		Mockito.when(recipesRepository.existsById(Mockito.anyLong())).thenReturn(false);
		try {
			 recipesController.deleteRecipe(10L);
		} catch (RecipesException ex) {
			
		}
	}
	
	/**
	 * Update a recipes
	 * mocking the existsById() Method with false and for save method is mocked with a recipes object
	 * @throws Exception
	 */
	@Test
	public void updateRecipesWithPositiveScenario() throws Exception{
		Mockito.when(recipesRepository.existsById(Mockito.anyLong())).thenReturn(true);
		Mockito.when(recipesRepository.save(Mockito.any(Recipes.class))).thenReturn(getRecipe());
		try {
			String result = recipesController.modifyRecipe(getRecipeDTO());
			assertEquals( "Recipe successfully updated!", result);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Update a recipes
	 * mocking the existsById() Method with true 
	 * @throws Exception
	 */
	@Test
	public void updateRecipesWithNegativeeScenario() throws Exception{
		Mockito.when(recipesRepository.existsById(Mockito.anyLong())).thenReturn(false);
		try {
			 recipesController.modifyRecipe(getRecipeDTO());
		} catch (RecipesException ex) {
			
		}
	}
	
	private RecipesDTO getRecipeDTO() {
		UsedIngredientsDTO usedIn1 = new UsedIngredientsDTO();
		usedIn1.setUsedIngredientId(1);
		usedIn1.setUsedIngredientsName("turmuric");
		UsedIngredientsDTO usedIn2 = new UsedIngredientsDTO();
		usedIn2.setUsedIngredientId(2);
		usedIn2.setUsedIngredientsName("Oil");
		Set<UsedIngredientsDTO> set = new HashSet<>();
		set.add(usedIn1);
		set.add(usedIn2);
		
		RecipesDTO recipes = new RecipesDTO();
		recipes.setRecipesId(1L);
		recipes.setRecipesName("PaneervMasala");
		recipes.setRecipesType("Veg");
		recipes.setNoOfPerson(2);
		recipes.setCookingInstruction("xyz");
		recipes.setUsedingredients(set);
		return recipes;
	}
	
	private Recipes getRecipe() {
		UsedIngredients usedIn1 = new UsedIngredients();
		usedIn1.setUsedIngredientId(1);
		usedIn1.setUsedIngredientsName("turmuric");
		UsedIngredients usedIn2 = new UsedIngredients();
		usedIn2.setUsedIngredientId(2);
		usedIn2.setUsedIngredientsName("Oil");
		Set<UsedIngredients> set = new HashSet<>();
		set.add(usedIn1);
		set.add(usedIn2);
		
		Recipes recipes = new Recipes();
		recipes.setRecipesId(1L);
		recipes.setRecipesName("PaneervMasala");
		recipes.setRecipesType("Veg");
		recipes.setNoOfPerson(2);
		recipes.setCookingInstruction("xyz");
		recipes.setUsedingredients(set);
		return recipes;
	}
	
	
}
