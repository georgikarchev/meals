package com.whatwillieat.meals.repository;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.Ingredient;
import com.whatwillieat.meals.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    List<Ingredient> findByDietaryCategoriesContaining(Set<DietaryCategory> dietaryCategories);

    // Find all ingredients by dietary category (dietaryCategories set contains the given category)
    // @Query("SELECT a FROM Ingredient a WHERE :dietaryCategory MEMBER OF a.dietaryCategories")
    List<Ingredient> findByDietaryCategoriesContaining(DietaryCategory dietaryCategory);
}
