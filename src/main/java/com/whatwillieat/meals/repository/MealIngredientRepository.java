package com.whatwillieat.meals.repository;

import com.whatwillieat.meals.model.Ingredient;
import com.whatwillieat.meals.model.Meal;
import com.whatwillieat.meals.model.MealIngredient;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface MealIngredientRepository extends JpaRepository<MealIngredient, UUID> {
    List<MealIngredient> findMealByIngredient(Ingredient ingredient);

    @Query("SELECT mi.meal FROM MealIngredient mi WHERE mi.ingredient.id = :ingredientId")
    List<Meal> findMealsByIngredient(@Param("ingredientId") UUID ingredientId);
}
