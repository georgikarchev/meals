package com.whatwillieat.meals.repository;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.Meal;
import com.whatwillieat.meals.model.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface MealRepository extends JpaRepository<Meal, UUID> {

    // Find all meals by meal type (mealTypes set contains the given type)
    // @Query("SELECT m FROM Meal m WHERE :mealType MEMBER OF m.mealTypes")
    List<Meal> findByMealTypesContaining(MealType mealType);

    List<Meal> findByDietaryCategoriesContaining(Set<DietaryCategory> dietaryCategories);

    // Find all meals by dietary category (dietaryCategories set contains the given category)
    // @Query("SELECT m FROM Meal m WHERE :dietaryCategory MEMBER OF m.dietaryCategories")
    List<Meal> findByDietaryCategoriesContaining(DietaryCategory dietaryCategory);

    // Find all meals by both meal type and dietary category
    // @Query("SELECT m FROM Meal m WHERE :mealType MEMBER OF m.mealTypes AND :dietaryCategory MEMBER OF m.dietaryCategories")
    List<Meal> findByMealTypesContainingAndDietaryCategoriesContaining(MealType mealType, DietaryCategory dietaryCategory);

    List<Meal> findByIsDeletedFalse();

    List<Meal> findByIsDeletedTrue();
}

