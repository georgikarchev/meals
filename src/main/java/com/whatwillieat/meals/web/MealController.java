package com.whatwillieat.meals.web;

import com.whatwillieat.meals.model.*;
import com.whatwillieat.meals.service.IngredientService;
import com.whatwillieat.meals.service.MealService;
import com.whatwillieat.meals.web.dto.AddIngredientRequest;
import com.whatwillieat.meals.web.dto.MealRequest;
import com.whatwillieat.meals.web.dto.MealResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
                        .description(mealRequest.getDescription())
                        .dietaryCategories(mealRequest.getDietaryCategories())
                        .mealTypes(mealRequest.getMealTypes())
                        .build())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable UUID id, @RequestBody MealRequest mealRequest) {
        Meal meal = mealService.updateMeal(id, mealRequest);
        return ResponseEntity.ok(meal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable UUID id) {
        mealService.softDeleteMeal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealResponse> getMeal(@PathVariable UUID id) {
        return ResponseEntity.ok(mealService.getMeal(id));
    }

    @GetMapping
    public ResponseEntity<List<Meal>> getMeals() {
        return ResponseEntity.ok(mealService.getNonSoftDeletedMeals());
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<Meal>> getDeletedMeals() {
        return ResponseEntity.ok(mealService.getSoftDeletedMeals());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getMealsCount() {
        return ResponseEntity.ok(mealService.getMealsCount());
    }

    @PostMapping("/{mealId}/ingredients")
    public ResponseEntity<MealResponse> addIngredientToMeal(
            @PathVariable UUID mealId,
            @RequestBody AddIngredientRequest request) {
        Meal meal = mealService.getMealOrThrow(mealId);
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
