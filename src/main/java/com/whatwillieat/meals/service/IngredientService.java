package com.whatwillieat.meals.service;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.Ingredient;
import com.whatwillieat.meals.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void delete(UUID id) {
        Ingredient ingredient = getIngredientOrThrow(id);
        ingredientRepository.delete(ingredient);
    }

    public void softDelete(UUID id) {
        Ingredient item = getIngredientOrThrow(id);
        item.setDeleted(true);
        ingredientRepository.save(item);
    }

    public Ingredient getIngredient(UUID id) {
        return getIngredientOrThrow(id);
    }

    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    public List<Ingredient> getIngredientsByType(DietaryCategory type) {
        return ingredientRepository.findByType(type);
    }

    private Ingredient getIngredientOrThrow(UUID id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));
    }
}
