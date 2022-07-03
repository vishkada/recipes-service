package com.task.favourite.recipes.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.task.favourite.recipes.util.DateUtil;
import com.task.favourite.recipes.dto.RecipesDTO;
import com.task.favourite.recipes.dto.UsedIngredientsDTO;
import com.task.favourite.recipes.entity.Recipes;
import com.task.favourite.recipes.entity.UsedIngredients;
import com.task.favourite.recipes.exception.ErrorMessage;
import com.task.favourite.recipes.exception.RecipesException;
import com.task.favourite.recipes.repository.RecipesRepository;
import com.task.favourite.recipes.service.RecipesService;

import lombok.extern.log4j.Log4j2;

/**
 * {@link RecipesServiceImpl}
 * 
 * RecipesServiceImpl is used to write business logic for Recipes
 * 
 * @author Vishwas_Kadam
 *
 */

@Log4j2
@Service
public class RecipesServiceImpl implements RecipesService {
	
	@Autowired
	private RecipesRepository repository; 
	
	/**
	 * Code to fetch all recipes available
	 * Checking first if recipe is is available, if not  throwing the message : No Repipe found!
	 * if recipe is available proceeding with fetching the recipes
	 */
	public List<RecipesDTO> getRecipes() throws RecipesException{
		log.info(" In fetchRecipes() of  RecipesServiceImpl.");
		
		/**
		 * Fetch all recipes
		 */
		List<Recipes> recipesList=  repository.findAll();
		
		if (recipesList==null || recipesList.size()==0) {
			log.error("In fetchRecipes() of  RecipesServiceImpl : No Recipes found!");
			ErrorMessage message = new ErrorMessage();
			RecipesException	ex =  new RecipesException();
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setMessage("No Repipe found!");
			ex.setErrorMessage(message);
			throw ex;
		}
		
		List<RecipesDTO> recipesDTOList = new ArrayList<>();
		/**
		 *  Convert recipesListt to List of RecipesDTO List
		 */
		recipesList.forEach(recipe -> {
			RecipesDTO recipesDTO = new RecipesDTO();
			/**
			 *  Convert ingredients List to ingredientsDTO List Object
			 */
			fetchUsedIngredients(recipe, recipesDTO);
			recipesDTO.setRecipesId(recipe.getRecipesId());
			recipesDTO.setRecipesName(recipe.getRecipesName());
			recipesDTO.setRecipesType(recipe.getRecipesType());
			recipesDTO.setNoOfPerson(recipe.getNoOfPerson());
			recipesDTO.setCookingInstruction(recipe.getCookingInstruction());
			recipesDTO.setUpdatedAt(recipe.getUpdatedAt());
			recipesDTO.setPreparedAt(recipe.getPreparedAt());
			recipesDTOList.add(recipesDTO);
		});
		return  recipesDTOList;
	}
	/**
	 * Adding new recipe
	 * Checking first if recipe is available, if not  throwing the message : Recipe already exists, can't be added!
	 * if recipe is available proceeding with save recipe
	 */
	public void saveRecipe(RecipesDTO recipesDTO) throws RecipesException {
		log.info(" In saveRecipes() of  RecipesServiceImpl ");
		
		ErrorMessage message = new ErrorMessage();
		RecipesException	ex =  new RecipesException();
		/**
		 * Check the recipe if available by recipe name
		 */
		Optional<Recipes> optional = repository.findByRecipesName(recipesDTO.getRecipesName()); 
		if(optional.isPresent()) {
			log.error("In saveRecipe() of  RecipesServiceImpl :Recipe already exists, can't be added!");
			message.setStatus(HttpStatus.FOUND);
			message.setMessage("Recipe already exists, can't be added!");
			ex.setErrorMessage(message);
			throw ex;
		}  
		/**
		 *  Convert recipesDTO to Recipes Object
		 */
		Recipes recipe = new Recipes();
		BeanUtils.copyProperties(recipesDTO, recipe);
		
		Set<UsedIngredients> usedIngredients =recipesDTO.getUsedingredients().stream()
				.map(ingredient-> new UsedIngredients(ingredient.getId(), ingredient.getUsedIngredientId(),ingredient.getUsedIngredientName() ))
       		 .collect(Collectors.toSet());
        
		recipe.setUsedingredients(usedIngredients);
		recipe.setPreparedAt(DateUtil.getCurrentDateTime());
		try {
			repository.save(recipe);
		} catch (Exception exe) {
			log.error("In saveRecipe() of  RecipesServiceImpl :Something went wrong while saving Recipe!");
			message.setStatus(HttpStatus.BAD_REQUEST);
			message.setMessage("Something went wrong while saving Recipe!");
			ex.setErrorMessage(message);
			throw ex;
		}
	}
	/**
	 * Function to delete recipe By recipesId
	 *  Check first if recipe is available, if not  throwing the message :Recipe not found, can't be deleted!
	 * if recipe is available proceeding with delete recipe by id
	 */
	public void deleteRecipe(Long recipesId) throws RecipesException {
		log.info(" In deleteRecipes() of  RecipesServiceImpl ");
		
		ErrorMessage message = new ErrorMessage();
		RecipesException	ex =  new RecipesException();
		/**
		 * Check the recipe if available by recipe id
		 */
		if (!repository.existsById(recipesId)) {
			log.error("In deleteRecipe() of  RecipesServiceImpl :Recipe not found, can't be deleted!");
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setMessage("Recipe not found, can't be deleted!");
			ex.setErrorMessage(message);
			throw ex;
		}
		/**
		 * delete recipe by recipesId
		 */
		try {
			repository.deleteById(recipesId);
		} catch (Exception exe) {
			log.error("In deleteRecipe() of  RecipesServiceImpl :Something went wrong while deleting Recipe!");
			message.setStatus(HttpStatus.BAD_REQUEST);
			message.setMessage("Something went wrong while deleting Recipe!");
			ex.setErrorMessage(message);
			throw ex;
		}
	}
	
	/**
	 * Function to delete recipe By deleteRecipeByName
	 *  Checking first if recipe is available, if not  throwing the message :Recipe not found, can't be deleted!
	 * if recipe is available proceeding with delete recipe by recipe name
	 */
	public void deleteRecipeByName(String recipesName)  throws RecipesException {
		log.info(" In deleteRecipeByName() of  RecipesServiceImpl ");
		
		ErrorMessage message = new ErrorMessage();
		RecipesException	ex =  new RecipesException();
		/**
		 * Check the recipe if available by recipe name
		 */
		Optional<Recipes> optional = repository.findByRecipesName(recipesName); 
		if(!optional.isPresent()) {
			log.error("In deleteRecipeByName() of  RecipesServiceImpl :Recipe does not found, can't be deleted!");
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setMessage("Recipe does not found, can't be deleted!");
			ex.setErrorMessage(message);
			throw ex;
		}  
		/**
		 * Delete recipe by recipesId
		 */
		Long recipeId = optional.get().getRecipesId();
		try {
			repository.deleteById(recipeId);
		} catch (Exception exe) {
			log.error("In deleteRecipeByName() of  RecipesServiceImpl :Something went wrong while deleting Recipe!");
			message.setStatus(HttpStatus.BAD_REQUEST);
			message.setMessage("Something went wrong while deleting Recipe!");
			ex.setErrorMessage(message);
			throw ex;
		}
	}
	
	/**
	 * Function to update recipe 
	 * Check first if recipe is is available, if not  throwing the message : Recipe does not exists can not be updated
	 * if recipe is available proceeding with update
	 */
	public void  updateRecipe(RecipesDTO recipesDTO) throws RecipesException{

		log.info(" In updateRecipes() of  RecipesServiceImpl ");
		
		ErrorMessage message = new ErrorMessage();
		RecipesException	ex =  new RecipesException();
		/**
		 * check the recipe if available by recipe id
		 */
		//Optional<Recipes> optional = repository.findByRecipesName(recipesDTO.getRecipesName()); 
		if(!repository.existsById(recipesDTO.getRecipesId())) {
			log.error("In updateRecipe() of  RecipesServiceImpl :Recipe does not exists can not be updated!");
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setMessage("Recipe does not exists can not be updated!");
			ex.setErrorMessage(message);
			throw ex;
		}  
		/**
		 *  Convert recipesDTO to Recipes Object
		 */
		Recipes recipe = new Recipes();
		BeanUtils.copyProperties(recipesDTO, recipe);
		
		recipe.setUpdatedAt((DateUtil.getCurrentDateTime()));
		Set<UsedIngredients> usedIngredients =recipesDTO.getUsedingredients().stream()
				.map(ingredient-> new UsedIngredients(ingredient.getId(), ingredient.getUsedIngredientId(),ingredient.getUsedIngredientName() ))
       		 .collect(Collectors.toSet());
        
		recipe.setUsedingredients(usedIngredients);
		
		/**
		 * Update the recipe
		 */
		try {
			repository.save(recipe);
		}catch (Exception exe) {
			log.error("In updateRecipe() of  RecipesServiceImpl :Something went wrong while updating Recipe!");
			message.setStatus(HttpStatus.BAD_REQUEST);
			message.setMessage("Something went wrong while updating Recipe!");
			ex.setErrorMessage(message);
			throw ex;
		}
	}
	/**
	 *  Convert ingredients List to ingredientsDTO List Object
	 */
	private void fetchUsedIngredients(Recipes recipe, RecipesDTO recipesDTO) {
		Set<UsedIngredientsDTO> usedIngredients = new HashSet<UsedIngredientsDTO>();
		if(!recipe.getUsedingredients().isEmpty()) {
			usedIngredients =recipe.getUsedingredients().stream()
					.map(ingredient-> new UsedIngredientsDTO(ingredient.getId(), ingredient.getUsedIngredientId(),ingredient.getUsedIngredientName() ))
	       		 .collect(Collectors.toSet());
		}
		recipesDTO.setUsedingredients(usedIngredients);
	}
}
