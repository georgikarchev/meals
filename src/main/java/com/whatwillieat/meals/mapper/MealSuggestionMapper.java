package com.whatwillieat.meals.mapper;

import com.whatwillieat.meals.dto.MealSuggestion;
import com.whatwillieat.meals.model.Meal;
import org.springframework.stereotype.Component;

@Component
public class MealSuggestionMapper {
    public MealSuggestion fromEntity(Meal meal) {
        return MealSuggestion.builder()
                .id(meal.getId())
                .name(meal.getName())
                .description(meal.getDescription())
                .dietaryCategories(meal.getDietaryCategories())
                .mealTypes(meal.getMealTypes())
                .build();
    }
}
