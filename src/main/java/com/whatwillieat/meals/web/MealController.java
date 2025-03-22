package com.whatwillieat.meals.web;

import com.whatwillieat.meals.model.*;
import com.whatwillieat.meals.service.IngredientService;
import com.whatwillieat.meals.service.MealService;
import com.whatwillieat.meals.web.dto.AddIngredientRequest;
import com.whatwillieat.meals.web.dto.MealRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("${app.API_V1_BASE_URL}/meals")
public class MealController {
    private final MealService mealService;
    private final IngredientService ingredientService;

    public MealController(MealService mealService, IngredientService ingredientService) {
        this.mealService = mealService;
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<Meal> createMeal(@RequestBody MealRequest mealRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                mealService.saveMeal(Meal.builder()
                        .name(mealRequest.getName())
                        .dietaryCategories(mealRequest.getDietaryCategories())
                        .mealTypes(mealRequest.getMealTypes())
                        .build())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable UUID id) {
        mealService.softDeleteMeal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMeal(@PathVariable UUID id) {
        return ResponseEntity.ok(mealService.getMeal(id));
    }

    @GetMapping
    public ResponseEntity<List<Meal>> getMeals() {
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getMealsCount() {
        return ResponseEntity.ok(mealService.getMealsCount());
    }

    @PostMapping("/{mealId}/ingredients")
    public ResponseEntity<Meal> addIngredientToMeal(
            @PathVariable UUID mealId,
            @RequestBody AddIngredientRequest request) {
        Meal meal = mealService.getMeal(mealId);
        Ingredient ingredient = ingredientService.getIngredient(request.getIngredientId());
        mealService.addIngredientToMeal(meal, ingredient, request.getQuantity(), request.getUnitOfMeasurement());
        return ResponseEntity.ok(mealService.getMeal(mealId));
    }

    @DeleteMapping("/{mealId}/ingredients/{ingredientId}")
    public ResponseEntity<Void> removeIngredientFromMeal(@PathVariable UUID mealId, @PathVariable UUID ingredientId) {
        mealService.removeIngredientFromMeal(mealId, ingredientId);
        return ResponseEntity.noContent().build();
    }
}
