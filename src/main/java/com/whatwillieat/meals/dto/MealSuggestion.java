package com.whatwillieat.meals.dto;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.MealType;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class MealSuggestion {
    private UUID id;
    private String name;
    private String description;
    private Set<DietaryCategory> dietaryCategories;
    private Set<MealType> mealTypes;
}
