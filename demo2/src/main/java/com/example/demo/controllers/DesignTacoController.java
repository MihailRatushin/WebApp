package com.example.demo.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.model.Ingredient;
import com.example.demo.model.Ingredient.Type;
import com.example.demo.model.Pie;
import com.example.demo.model.PieOrder;
import com.example.demo.repository.IngredientRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("pieOrder")
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepository;
	
	public DesignTacoController(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}
	
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		
//				List<Ingredient> ingredients = Arrays.asList(
//			      new Ingredient("YST", "Yeast", Type.DOUGH),
//			      new Ingredient("YSTFR", "Yeast free", Type.DOUGH),
//			      new Ingredient("PP", "Puff Pastry", Type.DOUGH),
//			      new Ingredient("PO", "Pork", Type.PROTEIN),
//			      new Ingredient("CH", "Chicken", Type.PROTEIN),
//			      new Ingredient("TO", "Tomatoes", Type.VEGGIES),
//			      new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//			      new Ingredient("ON", "Onion", Type.VEGGIES),
//			      new Ingredient("CHED", "Cheddar", Type.CHEESE),
//			      new Ingredient("SL", "Suluguni", Type.CHEESE)
//			      
//			    );
				
		
		
		Iterable<Ingredient> ingredients= ingredientRepository.findAll();
		Type[] types = Ingredient.Type.values();
		for(Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), 
					filterByType( (List<Ingredient>)ingredients, type));
			
		}
	}
	
	@ModelAttribute(name = "pieOrder")
	public PieOrder order() {
		return new PieOrder();
	}
	
	@ModelAttribute(name = "pie")
	public Pie pie() {
		return new Pie();
	}
	
	@GetMapping
	
	public String showDesignForm() {
		return "design";
	}
	
	 @PostMapping
	 public String processTaco(@Valid Pie pie, Errors errors,
	            @ModelAttribute PieOrder pieOrder) {
		 if(errors.hasErrors()) {
			 return "design";
		 }
	  pieOrder.addPie(pie);
	  log.info("Processing taco: {}", pie);
	  return "redirect:/orders/current";
	 }
	
	private Iterable<Ingredient> filterByType(
			List<Ingredient> ingredients, Type type){
		return ingredients.stream()
				.filter(ingredient -> ingredient.getType().equals(type))
				.collect(Collectors.toList());
		
	}
	
}
