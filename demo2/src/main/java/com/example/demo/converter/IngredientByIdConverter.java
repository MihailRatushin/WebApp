package com.example.demo.converter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.model.Ingredient;
import com.example.demo.repository.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
	
	private IngredientRepository ingredientRepository;
	
	@Autowired
	public IngredientByIdConverter(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}
	
//	private Map<String, Ingredient> ingredientMap = new HashMap<>();
	
//	public IngredientByIdConverter() {
			
//			ingredientMap.put("YST", new Ingredient("YST", "Yeast", Type.DOUGH));
//			ingredientMap.put("YSTFR", new Ingredient("YSTFR", "Yeast free", Type.DOUGH));
//			ingredientMap.put("PP", new Ingredient("PP", "Puff Pastry", Type.DOUGH));
//			ingredientMap.put("PO", new Ingredient("PO", "Pork", Type.PROTEIN));
//			ingredientMap.put("CH", new Ingredient("CH", "Chicken", Type.PROTEIN));
//			ingredientMap.put("TO", new Ingredient("TO", "Tomatoes", Type.VEGGIES));
//			ingredientMap.put("LETC", new Ingredient("LETC", "Lettuce", Type.VEGGIES));
//			ingredientMap.put("ON", new Ingredient("ON", "Onion", Type.VEGGIES));
//			ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Type.CHEESE));
//			ingredientMap.put("SL", new Ingredient("SL", "Suluguni", Type.CHEESE));

	      
//	}

	@Override
	public Ingredient convert(String id) {
//		return ingredientMap.get(source);
		return ingredientRepository.findById(id).orElse(null);
		
	}
		
}


