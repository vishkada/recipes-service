package com.task.favourite.recipes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.favourite.recipes.entity.Recipes;
/**
 * {@link RecipesRepository}
 * 
 * RecipesRepository interface is for JPA functionality and to perform CRUD operations.
 * 
 * @author Vishwas_Kadam
 */

@Repository
public interface RecipesRepository extends JpaRepository<Recipes, Long> {

	public  Optional<Recipes> findByRecipesName(String recipesName);
	
}
