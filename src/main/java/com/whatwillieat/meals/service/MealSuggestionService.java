package com.whatwillieat.meals.service;

import com.whatwillieat.meals.mapper.MealSuggestionMapper;
import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.Meal;
import com.whatwillieat.meals.model.MealType;
import com.whatwillieat.meals.dto.MealSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MealSuggestionService {

    private final MealService mealService;
    private final MealSuggestionMapper mealSuggestionMapper;

    @Autowired
    public MealSuggestionService(MealService mealService, MealSuggestionMapper mealSuggestionMapper) {
        this.mealService = mealService;
        this.mealSuggestionMapper = mealSuggestionMapper;
    }

    public List<MealSuggestion> getSuggestions(
            UUID userId,
            MealType mealType,
            DietaryCategory dietaryCategory) {

        // get user history
        // choose 2 highest rated meals from user history, which were not recently consumed (in the last 2 weeks)
        // choose 1 random meal, which is not present in user history -> if no such meal exists, choose a random meal, which was not recently consumed
        // return a list of 3 MealSuggestions

        List<Meal> mealsOfTypeAndCategory = mealService
                .getMealsByTypeAndCategory(mealType, dietaryCategory);

        return mealsOfTypeAndCategory
                .stream()
                .map( mealSuggestionMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
