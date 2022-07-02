package com.task.favourite.recipes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.task.favourite.recipes.dto.RecipesDTO;
import com.task.favourite.recipes.entity.Recipes;
import com.task.favourite.recipes.exception.RecipesException;
import com.task.favourite.recipes.service.RecipesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;

/**
 * {@link RecipesController}
 * 
 * Recipes controller to handle the request for Add, delete, update, fetch  operations
 *  
 * @author Vishwas_Kadam
 *
 */

@Log4j2
@RestController
public class RecipesController {

	@Autowired
	private RecipesService recepesService;
	
	@GetMapping(value = "/view/recipes")
	@Operation(
	        summary = "Fetch all recipes",
	        description = "Get list of all available Recipes",
	        tags = { "Recipes" },
	        responses = {
	            @ApiResponse(
	                description = "Success",
	                responseCode = "200",
	                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipes.class))
	            ),
	            @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
	            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
	        }
	    )
	public ResponseEntity<List<RecipesDTO>>  getRecipes() throws RecipesException {
		log.info(" In fetchRecipes() of  RecipesController ");
		List<RecipesDTO> recipesList =  recepesService.getRecipes();
		return  new ResponseEntity<List<RecipesDTO>>(recipesList, HttpStatus.OK);		
	}
	
	@PostMapping(value = "/manage/recipe")
	@Operation(
	        summary = "Add new recipe",
	        description = "Add new recipe to recpies",
	        tags = { "Recipes" },
	        responses = {
	            @ApiResponse(
	                description = "Success",
	                responseCode = "200",
	                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipes.class))
	            ),
	            @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
	            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
	        }
	    )
	public String  addRecipe(@RequestBody RecipesDTO recipesDTO) throws RecipesException {
		log.info(" In addRecipe() of  RecipesController ");
		 recepesService.saveRecipe(recipesDTO);
		 return "Recipe is added successfully";
	}
	
	@PutMapping(value = "/manage/recipe")
	@Operation(
	        summary = "Update exising recipe",
	        description = "Updating existing recipe",
	        tags = { "Recipes" },
	        responses = {
	            @ApiResponse(
	                description = "Success",
	                responseCode = "200",
	                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipes.class))
	            ),
	            @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
	            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
	        }
	    )
	public String  modifyRecipe( @RequestBody RecipesDTO recipesDTO) throws RecipesException {
		log.info(" In updateRecipe() of  RecipesController ");
		 recepesService.updateRecipe(recipesDTO);
		 return "Recipe successfully updated!";
	}
	
	@DeleteMapping(value = "/manage/recipe/id/{id}")
	@Operation(
	        summary = "Delete recipe by Id",
	        description = "Delete recipe by recpie id",
	        tags = { "Recipes" },
	        responses = {
	            @ApiResponse(
	                description = "Success",
	                responseCode = "200",
	                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipes.class))
	            ),
	            @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
	            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
	        }
	    )
	public String deleteRecipe(@PathVariable("id") Long recipesId) throws RecipesException {
		log.info(" In deleteRecipe() of  RecipesController ");
		 recepesService.deleteRecipe(recipesId);
		 return "Recipe is deleted successfully";
	}
	
	@DeleteMapping(value = "/manage/recipe/name/{recipeName}")
	@Operation(
	        summary = "Delete recipe by name",
	        description = "Delete recipe by recpie name",
	        tags = { "Recipes" },
	        responses = {
	            @ApiResponse(
	                description = "Success",
	                responseCode = "200",
	                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipes.class))
	            ),
	            @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
	            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
	        }
	    )
	public String deleteRecipeByrecipeName(@PathVariable("recipeName") String recipeName) throws RecipesException {
		log.info(" In deleteRecipe() of  RecipesController ");
		 recepesService.deleteRecipeByName(recipeName);
		 return "Recipe is deleted successfully";
	}
	
}
