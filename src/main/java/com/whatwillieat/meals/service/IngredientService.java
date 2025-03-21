package com.whatwillieat.meals.service;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.Ingredient;
import com.whatwillieat.meals.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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

    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public Long getIngredientCount() { return ingredientRepository.count(); }

    public List<Ingredient> getIngredientsByDietaryCategory(DietaryCategory dietaryCategory) {
        return ingredientRepository.findByDietaryCategoriesContaining(Set.of(dietaryCategory));
    }

    public List<Ingredient> getIngredientsByDietaryCategories(DietaryCategory dietaryCategory) {
        return ingredientRepository.findByDietaryCategoriesContaining(Set.of(dietaryCategory));
    }

    private Ingredient getIngredientOrThrow(UUID id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));
    }
}
