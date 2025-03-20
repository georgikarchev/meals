package com.whatwillieat.meals.web;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.MealType;
import com.whatwillieat.meals.service.MealService;
import com.whatwillieat.meals.service.MealSuggestionService;
import com.whatwillieat.meals.dto.MealSuggestion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${app.API_V1_BASE_URL}/meals")
public class MealController {
    private final MealService mealService;
    private final MealSuggestionService suggestionService;

    public MealController(MealService mealService, MealSuggestionService suggestionService) {
        this.mealService = mealService;
        this.suggestionService = suggestionService;
    }

    @GetMapping("/{userId}/suggestions")
    public ResponseEntity<List<MealSuggestion>> getSuggestions(
            @PathVariable("userId") UUID userId,
            @RequestParam(required = false) MealType mealType,
            @RequestParam(required = false) DietaryCategory dietaryCategory) {

        List<MealSuggestion> suggestions =
                suggestionService.getSuggestions(userId, mealType, dietaryCategory);

        return  ResponseEntity.ok()
                .body(suggestions);
    }
}
