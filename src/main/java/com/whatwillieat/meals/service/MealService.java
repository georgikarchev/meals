package com.whatwillieat.meals.service;

import com.whatwillieat.meals.model.*;
import com.whatwillieat.meals.repository.IngredientRepository;
import com.whatwillieat.meals.repository.MealIngredientRepository;
import com.whatwillieat.meals.repository.MealRepository;
import com.whatwillieat.meals.web.dto.MealRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
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

    @Transactional
    public Meal updateMeal(UUID mealId, MealRequest updatedMeal) {
        return mealRepository.findById(mealId)
                .map(existingMeal -> {
                    // Update only fields that are non-null in the updatedMeal (DTO)
                    if (updatedMeal.getName() != null) {
                        existingMeal.setName(updatedMeal.getName());
                    }
                    if (updatedMeal.getDescription() != null) {
                        existingMeal.setDescription(updatedMeal.getDescription());
                    }
                    if (updatedMeal.getDietaryCategories() != null) {
                        existingMeal.setDietaryCategories(updatedMeal.getDietaryCategories());
                    }
                    if (updatedMeal.getMealTypes() != null) {
                        existingMeal.setMealTypes(updatedMeal.getMealTypes());
                    }

                    return mealRepository.save(existingMeal);
                })
                .orElseThrow(() -> new EntityNotFoundException("Meal not found with id: " + mealId));
    }

    public Meal getMeal(UUID id) {
        return getMealOrThrow(id);
    }

    public List<Meal> getAllMeals() { return mealRepository.findAll(); }

    public Long getMealsCount() { return mealRepository.count(); }

    public Meal getMealOrThrow(UUID id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meal not found"));
    }

    // Add ingredient to meal
    @Transactional
    public void addIngredientToMeal(Meal meal, Ingredient ingredient, Double quantity, UnitOfMeasurement unitOfMeasurement) {

        if (meal == null) {
            throw new IllegalArgumentException("Meal cannot be null");
        }
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (unitOfMeasurement == null) {
            throw new IllegalArgumentException("Unit of measurement cannot be null");
        }

        MealIngredient mealIngredient = new MealIngredient(meal, ingredient, quantity, unitOfMeasurement);

        meal.getIngredients().add(mealIngredient);
        mealRepository.save(meal);
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
