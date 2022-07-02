package com.task.favourite.recipes.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.task.favourite.recipes.util.DateUtil;
import com.task.favourite.recipes.dto.IngredientDTO;
import com.task.favourite.recipes.entity.Ingredients;
import com.task.favourite.recipes.exception.ErrorMessage;
import com.task.favourite.recipes.exception.IngredientException;
import com.task.favourite.recipes.repository.IngredientRepository;
import com.task.favourite.recipes.service.IngredientService;

import lombok.extern.log4j.Log4j2;

/**
 * {@link IngredientServiceImpl}
 * 
 * IngredientServiceImpl is used to write business logic for Ingredients
 * 
 * @author Vishwas_Kadam
 *
 */
@Log4j2
@Service
public class IngredientServiceImpl  implements IngredientService{

	@Autowired
	private IngredientRepository repository; 
	
	/**
	 * Code to fetch all ingredients available
	 * Checking first if ingredient  is available, if not  throwing the message : No ingredients found!
	 * if recipe is available proceeding with fetching the ingredients
	 */
	public List<IngredientDTO> fetchIngredients()  throws IngredientException {
		
		log.info(" In fetchIngredients() of  IngredientServiceImpl ");
		ErrorMessage message = new ErrorMessage();
		IngredientException	ex =  new IngredientException();
		/**
		 * fetching all ingredients
		 */
		List<Ingredients> ingredientList = repository.findAll();
		
		if (ingredientList==null || ingredientList.size()==0) {
			log.error(" In fetchIngredients() of  IngredientServiceImpl : No Ingredients found!");
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setMessage("No Ingredients found!");
			ex.setErrorMessage(message);
			throw ex;
		}
		/**
		 *  Converting ingredient List to IngredientDTO List Object
		 */
		List<IngredientDTO> ingredientDTOList =
		ingredientList.stream().map(ingredient-> new IngredientDTO(ingredient.getIngredientId(),
																	ingredient.getIngredientName(),
																	ingredient.getCreatedAt(),
																	ingredient.getUpdatedAt() )).collect(Collectors.toList());
		
		return ingredientDTOList;
	}
	
	/**
	 * Adding new Ingredient
	 * Checking first if Ingredient is available, if not  throwing the message : Ingredient already exists, can't be added!
	 * if Ingredient is available proceeding with save Ingredient
	 */
	public IngredientDTO saveIngredient(IngredientDTO ingredientDTO) throws IngredientException {
		log.info(" In saveIngredient() of  IngredientServiceImpl ");
		ErrorMessage message = new ErrorMessage();
		IngredientException	ex =  new IngredientException();
		/**
		 * checking the ingredient, if available by ingredient name
		 */
		Optional<Ingredients> optional = repository.findByIngredientName(ingredientDTO.getIngredientName()); 
		if(optional.isPresent()) {
			log.error(" In saveIngredient() of  IngredientServiceImpl : Ingredient already exists, can't be added!");
			message.setStatus(HttpStatus.FOUND);
			message.setMessage("Ingredient already exists, can't be added!");
			ex.setErrorMessage(message);
			throw ex;
		}  
		try {
			/**
			 *  Converting ingredient tDTO to Ingredient Object
			 */
			Ingredients ingredient = new Ingredients();
			BeanUtils.copyProperties(ingredientDTO, ingredient);
			
			ingredient.setCreatedAt(DateUtil.getCurrentDateTime());
			ingredient.setUpdatedAt(DateUtil.getCurrentDateTime());
			
			/**
			 * saving the ingredient
			 */
			ingredient = repository.save(ingredient);
			
			BeanUtils.copyProperties(ingredient, ingredientDTO);
		} catch (Exception exe) {
			log.error(" In saveIngredient() of  IngredientServiceImpl : Something went wrong while saving Ingredient!");
			message.setStatus(HttpStatus.BAD_REQUEST);
			message.setMessage("Something went wrong while saving Ingredient!");
			ex.setErrorMessage(message);
			throw ex;
		}
		return ingredientDTO;
	}
	
	public void deleteIngredient(Long ingredientId) throws IngredientException{
		log.info(" In deleteIngredient() of  IngredientServiceImpl ");
		ErrorMessage message = new ErrorMessage();
		IngredientException	ex =  new IngredientException();
		/**
		 * checking the ingredient, if exists by ingredient id
		 */
		if (!repository.existsById(ingredientId)) {
			log.error(" In deleteIngredient() of  IngredientServiceImpl : Ingredient not found!");
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setMessage("Ingredient not found!");
			ex.setErrorMessage(message);
			throw ex;
		} 
		/**
		 * deleting the ingredient by ingredient Id
		 */
		try {
			repository.deleteById(ingredientId);
		}catch(Exception exe) {
			log.error(" In deleteIngredient() of  IngredientServiceImpl : Something went wrong while deleting Ingredient ");
			message.setStatus(HttpStatus.BAD_REQUEST);
			message.setMessage("Something went wrong while deleting Ingredient !");
			ex.setErrorMessage(message);
			throw ex;
		}
	}
	/**
	 * Function to delete Ingredient By ingredientName
	 *  Checking first if Ingredient is available, if not  throwing the message :Ingredient not found!
	 * if Ingredient is available proceeding with delete Ingredient by id
	 */
	public void deleteIngredientByName(String ingredientName) throws IngredientException{
		log.info(" In deleteIngredientByName() of  IngredientServiceImpl ");
		ErrorMessage message = new ErrorMessage();
		IngredientException	ex =  new IngredientException();
		/**
		 * checking the ingredient, if exists by ingredient name
		 */
		Optional<Ingredients> optional = repository.findByIngredientName(ingredientName); 
		if(!optional.isPresent()) {
			log.error(" In deleteIngredientByName() of  IngredientServiceImpl : Ingredient not found!");
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setMessage("Ingredient does not exists, can't be deleted!");
			ex.setErrorMessage(message);
			throw ex;
		}  
		/**
		 * deleting the ingredient by ingredient Id
		 */
		try {
			repository.deleteByIngredientName(ingredientName);
		}catch(Exception exe) {
			log.error(" In deleteIngredientByName() of  IngredientServiceImpl : Something went wrong while deleting Ingredient !");
			message.setStatus(HttpStatus.BAD_REQUEST);
			message.setMessage("Something went wrong while deleting Ingredient !");
			ex.setErrorMessage(message);
			throw ex;
		}
	}
	
}
