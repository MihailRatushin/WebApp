package com.example.demo.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Pie {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	@Size(min = 5, message = "your taco name must be at least 5 characters long")
	private String name;
	@NotNull
	@Size(min = 1, message = "you must choose at least 1 ingredient")
	@ManyToMany
	private List<Ingredient> ingredients = new ArrayList<>();
	
	private Date date = new Date();
	
	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}
}
