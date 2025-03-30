package com.whatwillieat.meals.web.dto;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.MealIngredient;
import com.whatwillieat.meals.model.MealType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class IngredientResponse {
    private UUID id;
    private String name;
    private String description;
    private Set<DietaryCategory> dietaryCategories;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private boolean isDeleted;
}
