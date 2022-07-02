package com.task.favourite.recipes.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="INGREDIENTS")
public class Ingredients {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ingredientId", updatable = false, nullable = false)
	private Long ingredientId;
	
	@Column(length = 100, unique = true, nullable = false)
	private String ingredientName;
	
	private Timestamp createdAt;
	
	private Timestamp updatedAt;
}
