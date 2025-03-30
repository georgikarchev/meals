package com.whatwillieat.meals.web.dto;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.Ingredient;
import com.whatwillieat.meals.model.MealIngredient;
import com.whatwillieat.meals.model.MealType;
import com.whatwillieat.meals.repository.MealIngredientRepository;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class MealResponse {
    private UUID id;
    private String name;
    private String description;
    private Set<DietaryCategory> dietaryCategories;
    private Set<MealType> mealTypes;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private boolean isDeleted;
    private Set<IngredientResponse> ingredients;
}
