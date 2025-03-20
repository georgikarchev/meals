package com.whatwillieat.meals.service;

import com.whatwillieat.meals.model.*;
import com.whatwillieat.meals.repository.IngredientRepository;
import com.whatwillieat.meals.repository.MealIngredientRepository;
import com.whatwillieat.meals.repository.MealRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MealService {
    private final MealRepository mealRepository;
    private final MealIngredientRepository mealIngredientRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public MealService(MealRepository mealRepository, MealIngredientRepository mealIngredientRepository, IngredientRepository ingredientRepository) {
        this.mealRepository = mealRepository;
        this.mealIngredientRepository = mealIngredientRepository;
        this.ingredientRepository = ingredientRepository;
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

    public List<Meal> getMeals() { return mealRepository.findAll(); }

    public Long getMealsCount() { return mealRepository.count(); }

    public List<Meal> getMealsByCategory(DietaryCategory category) {
        return mealRepository.findByDietaryCategoriesContaining(category);
    }

    public List<Meal> getMealsByType(MealType type) {
        return mealRepository.findByMealTypesContaining(type);
    }

    public List<Meal> getMealsByTypeAndCategory(MealType type, DietaryCategory category) {
        return mealRepository.findByMealTypesContainingAndDietaryCategoriesContaining(type, category);
    }

    public List<Meal> getMealsByIngredient(UUID ingredientId) {
        return mealIngredientRepository.findMealsByIngredient(ingredientId);
    }

    public Meal getMealOrThrow(UUID id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meal not found"));
    }

    // Add ingredient to meal
    @Transactional
    public void addIngredientToMeal(UUID mealId, UUID ingredientId, Double quantity, UnitOfMeasurement unitOfMeasurement) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new IllegalArgumentException("Meal not found"));

        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));

        MealIngredient mealIngredient = new MealIngredient(meal, ingredient, quantity, unitOfMeasurement);

        meal.getIngredients().add(mealIngredient);  // Add to meal's ingredients set
//        System.out.println("Now adding ingredient " + mealIngredient.getIngredient().getName() + " to " + meal.getName());
        mealRepository.save(meal);  // Save the updated meal
    }

    // Remove ingredient from meal
    public void removeIngredientFromMeal(UUID mealId, UUID ingredientId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new IllegalArgumentException("Meal not found"));

        MealIngredient mealIngredient = meal.getIngredients()
                .stream()
                .filter(mi -> mi.getIngredient().getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found in meal"));

        meal.getIngredients().remove(mealIngredient);  // Remove from meal's ingredients set

        mealRepository.save(meal);  // Save the updated meal
    }

}
