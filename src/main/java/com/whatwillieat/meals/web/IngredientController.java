package com.whatwillieat.meals.web;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.Ingredient;
import com.whatwillieat.meals.model.Meal;
import com.whatwillieat.meals.service.IngredientService;
import com.whatwillieat.meals.web.dto.IngredientRequest;
import com.whatwillieat.meals.web.dto.MealRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${app.API_V1_BASE_URL}/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.save(ingredient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable UUID id, @RequestBody IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientService.updateIngredient(id, ingredientRequest);
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> softDeleteIngredient(@PathVariable UUID id) {
        ingredientService.softDelete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable UUID id) {
        return ResponseEntity.ok(ingredientService.getIngredient(id));
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getNonSoftDeletedIngredients());
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<Ingredient>> getSoftDeletedIngredients() {
        return ResponseEntity.ok(ingredientService.getSoftDeletedIngredients());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getIngredientCount() {
        return ResponseEntity.ok(ingredientService.getIngredientCount());
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<Ingredient>> getIngredientsByCategory(@RequestParam DietaryCategory category) {
        return ResponseEntity.ok(ingredientService.getIngredientsByDietaryCategory(category));
    }
}
