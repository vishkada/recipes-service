package com.task.favourite.recipes.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="RECIPES")
public class Recipes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "recipesId", updatable = false, nullable = false)
	private Long recipesId;

	@Column(length = 150)
	private String recipesName;

	private String recipesType;

	private Integer noOfPerson;

	private String cookingInstruction;
	
	private Timestamp preparedAt;
	
	private Timestamp updatedAt;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name="RECIPESID_FK")
	private Set<UsedIngredients> usedingredients = new HashSet<>();

}
