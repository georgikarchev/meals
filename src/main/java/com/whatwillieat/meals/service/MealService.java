package com.whatwillieat.meals.service;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.Meal;
import com.whatwillieat.meals.model.MealIngredient;
import com.whatwillieat.meals.repository.MealIngredientRepository;
import com.whatwillieat.meals.repository.MealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MealService {
    private final MealRepository mealRepository;
    private final MealIngredientRepository mealIngredientRepository;

    @Autowired
    public MealService(MealRepository mealRepository, MealIngredientRepository mealIngredientRepository) {
        this.mealRepository = mealRepository;
        this.mealIngredientRepository = mealIngredientRepository;
    }

    public Meal saveMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public void delete(UUID id) {
        Meal meal = getMealOrThrow(id);
        mealRepository.delete(meal);
    }

    public void softDeleteMeal(UUID id) {
        Meal meal = getMealOrThrow(id);
        meal.setDeleted(true);
        mealRepository.save(meal);
    }

    public Meal getMeal(UUID id) {
        return getMealOrThrow(id);
    }

    public List<Meal> getMealsByType(DietaryCategory type) {
        return mealRepository.findByType(type);
    }

    public List<Meal> getMealsByIngredient(UUID ingredientId) {
        return mealIngredientRepository.findMealsByIngredient(ingredientId);
    }

    public Meal getMealOrThrow(UUID id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meal not found"));
    }

}
