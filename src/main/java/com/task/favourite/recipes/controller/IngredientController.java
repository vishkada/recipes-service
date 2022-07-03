package com.task.favourite.recipes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.task.favourite.recipes.dto.IngredientDTO;
import com.task.favourite.recipes.entity.Ingredients;
import com.task.favourite.recipes.exception.IngredientException;
import com.task.favourite.recipes.service.IngredientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
/**
 * {@link IngredientController}
 * 
 * Ingredients controller to handle the request for Add,  fetch operation

 * @author Vishwas_Kadam
 *
 */
@Log4j2
@RestController
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping(value = "/view/ingredients")
	@Operation(
	        summary = "Fetch all ingradients",
	        description = "Get list of all available Ingradients",
	        tags = { "Ingredients" },
	        responses = {
	            @ApiResponse(
	                description = "Success",
	                responseCode = "200",
	                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredients.class))
	            ),
	            @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
	            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
	        }
	    )
	public List<IngredientDTO> fetchIngredients() throws IngredientException{
		log.info(" In fetchIngredients() of  IngredientController ");
		List<IngredientDTO> ingredientList = ingredientService.fetchIngredients();
		return ingredientList;
	}

	@Operation(
	        summary = "Add new ingradient",
	        description = "Add new Ingradient",
	        tags = { "Ingredients" },
	        responses = {
	            @ApiResponse(
	                description = "Success",
	                responseCode = "200",
	                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredients.class))
	            ),
	            @ApiResponse(description = "Found", responseCode = "302", content = @Content),
	            @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
	            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
	        }
	    )
	@PostMapping(value = "/manage/ingredient")
	public IngredientDTO addIngredient(@RequestBody IngredientDTO ingredientDTO)  throws IngredientException {
		log.info(" In addIngredient() of  IngredientController ");
		return ingredientService.saveIngredient(ingredientDTO);
	}
	
	@Operation(
	        summary = "Delete ingradient by id",
	        description = "Delete Ingradient by Ingradient id",
	        tags = { "Ingredients" },
	        responses = {
	            @ApiResponse(
	                description = "Success",
	                responseCode = "200",
	                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredients.class))
	            ),
	            @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
	            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
	        }
	    )
	@DeleteMapping(value = "/manage/ingredient/id/{id}")
	public String deleteIngredient(@PathVariable("id") Long ingredientId) throws IngredientException{
		log.info(" In deleteIngredient() of  IngredientController ");
		 ingredientService.deleteIngredient(ingredientId);
		 return "Ingredient is deleted successfully";
	}
	

	@Operation(
	        summary = "Delete Ingradient by name",
	        description = "Delete Ingradient by Ingradient name",
	        tags = { "Ingredients" },
	        responses = {
	            @ApiResponse(
	                description = "Success",
	                responseCode = "200",
	                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredients.class))
	            ),
	            @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
	            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
	        }
	    )
	@DeleteMapping(value = "/manage/ingredient/name/{ingredientName}")
	public String deleteIngredientByName(@PathVariable("ingredientName") String ingredientName) throws IngredientException{
		log.info(" In deleteIngredient() of  IngredientController ");
		 ingredientService.deleteIngredientByName(ingredientName);
		 return "Ingredient is deleted successfully";
	}
}
