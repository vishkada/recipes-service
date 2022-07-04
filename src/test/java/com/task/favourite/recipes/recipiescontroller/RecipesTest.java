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
	private RecipesRepository recipesRepository;

	@Autowired
	private RecipesController recipesController;

	/**
	 * Adding a recipes which is not available in the DB We are mocking the save
	 * Method and findByRecipesName method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddRecipesTest() throws Exception {
		String result = recipesController.addRecipe(getRecipeDTO());
		assertEquals("Recipe is added successfully", result);
	}

	/**
	 * fetchRecipes mocking the findAll() Method with list of recipes
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetRecipes() throws Exception {
		List<Recipes> recipesList = new ArrayList<>();
		recipesList.add(getRecipe());
		Mockito.when(recipesRepository.findAll()).thenReturn(recipesList);
		ResponseEntity<List<RecipesDTO>> result = recipesController.getRecipes();
		assertNotNull(result.getBody());
	}

	/**
	 * deleteRecipe mocking the existsById() Method with true
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteRecipes() throws RecipesException {
		Mockito.when(recipesRepository.existsById(Mockito.anyLong())).thenReturn(true);
		Mockito.doNothing().when(recipesRepository).deleteById(Mockito.anyLong());
		String result = recipesController.deleteRecipe(10L);
		assertEquals("Recipe is deleted successfully", result);
	}

	/**
	 * Update a recipes mocking the existsById() Method with false and for save
	 * method is mocked with a recipes object
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateRecipes() throws Exception {
		Mockito.when(recipesRepository.existsById(Mockito.anyLong())).thenReturn(true);
		Mockito.when(recipesRepository.save(Mockito.any(Recipes.class))).thenReturn(getRecipe());
		String result = recipesController.modifyRecipe(getRecipeDTO());
		assertEquals("Recipe successfully updated!", result);
	}

	private RecipesDTO getRecipeDTO() {

		UsedIngredientsDTO usedIn1 = new UsedIngredientsDTO();
		usedIn1.setUsedIngredientId(1);
		usedIn1.setUsedIngredientName("jira");

		UsedIngredientsDTO usedIn2 = new UsedIngredientsDTO();
		usedIn2.setUsedIngredientId(2);
		usedIn2.setUsedIngredientName("Oil");

		Set<UsedIngredientsDTO> set = new HashSet<>();
		set.add(usedIn1);
		set.add(usedIn2);

		RecipesDTO recipes = new RecipesDTO();
		recipes.setRecipesId(1L);
		recipes.setRecipesName("Alo1o Gobi");
		recipes.setCookingInstruction("NA");
		recipes.setRecipesType("Veg");
		recipes.setNoOfPerson(6);
		recipes.setUsedingredients(set);
		return recipes;
	}

	private Recipes getRecipe() {

		UsedIngredients usedIn1 = new UsedIngredients();
		usedIn1.setUsedIngredientId(1);
		usedIn1.setUsedIngredientName("Chilli");

		UsedIngredients usedIn2 = new UsedIngredients();
		usedIn2.setUsedIngredientId(2);
		usedIn2.setUsedIngredientName("Oil");
		Set<UsedIngredients> set = new HashSet<>();
		set.add(usedIn1);
		set.add(usedIn2);

		Recipes recipes = new Recipes();
		recipes.setRecipesId(1L);
		recipes.setRecipesName("Chiken Masala");
		recipes.setRecipesType("Non-Veg");

		recipes.setNoOfPerson(2);
		recipes.setCookingInstruction("NA NA");
		recipes.setUsedingredients(set);
		return recipes;
	}

}
