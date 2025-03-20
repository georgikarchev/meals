package com.whatwillieat.meals.data;

import com.whatwillieat.meals.model.*;
import com.whatwillieat.meals.repository.IngredientRepository;
import com.whatwillieat.meals.repository.MealIngredientRepository;
import com.whatwillieat.meals.service.MealService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.whatwillieat.meals.repository.MealRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;
    private final MealIngredientRepository mealIngredientRepository;
    private final MealService mealService;

    public DatabaseSeeder(MealRepository mealRepository, IngredientRepository ingredientRepository, MealIngredientRepository mealIngredientRepository, MealService mealService) {
        this.mealRepository = mealRepository;
        this.ingredientRepository = ingredientRepository;
        this.mealIngredientRepository = mealIngredientRepository;
        this.mealService = mealService;
    }

    @Override
    public void run(String... args) {
        if (ingredientRepository.count() == 0) {  // Step 1: Insert ingredients if empty
            ingredientRepository.saveAll(List.of(
                    Ingredient.builder().name("Tomato").type(DietaryCategory.VEGAN).build(),
                    Ingredient.builder().name("Chicken").type(DietaryCategory.NON_VEGETARIAN).build(),
                    Ingredient.builder().name("Lettuce").type(DietaryCategory.VEGAN).build(),
                    Ingredient.builder().name("Pasta").type(DietaryCategory.GLUTEN_FREE).build(),
                    Ingredient.builder().name("Beef").type(DietaryCategory.NON_VEGETARIAN).build()
            ));
            System.out.println("Ingredients already exist. Skipping seeding.");
        }
        else {
            System.out.println("Ingredients already prepopulated.");
        }

        // Step 2: Retrieve ingredients by name
        Map<String, Ingredient> ingredients = ingredientRepository.findAll().stream()
                .collect(Collectors.toMap(Ingredient::getName, ing -> ing));

        // Step 3: Insert meals only if empty
        if (mealService.getMealsCount() == 0) {
            Meal grilledChickenSalad = Meal.builder()
                    .name("Grilled Chicken Salad")
                    .dietaryCategories(Set.of(DietaryCategory.NON_VEGETARIAN))
                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
                    .build();

            // Save the Meal first
            mealRepository.save(grilledChickenSalad);

            // Assign MealIngredients
            mealService.addIngredientToMeal(
                    grilledChickenSalad.getId(),
                    ingredients.get("Chicken").getId(),
                    150.0,
                    UnitOfMeasurement.GRAM);
//
//            // Now, create and assign MealIngredients using the builder
//            mealIngredientRepository.saveAll(List.of(
//                    MealIngredient.builder()
//                            .meal(grilledChickenSalad)
//                            .ingredient(ingredients.get("Chicken"))
//                            .build(),
//                    MealIngredient.builder()
//                            .meal(grilledChickenSalad)
//                            .ingredient(ingredients.get("Lettuce"))
//                            .build()
//            ));
//
//            Meal veganBuddhaBowl = Meal.builder()
//                    .name("Vegan Buddha Bowl")
//                    .dietaryCategories(Set.of(DietaryCategory.VEGAN))
//                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
//                    .build();
//            mealRepository.save(veganBuddhaBowl);
//            mealIngredientRepository.saveAll(List.of(
//                    MealIngredient.builder()
//                            .meal(veganBuddhaBowl)
//                            .ingredient(ingredients.get("Tomato"))
//                            .build(),
//                    MealIngredient.builder()
//                            .meal(veganBuddhaBowl)
//                            .ingredient(ingredients.get("Lettuce"))
//                            .build()
//            ));
//
//            Meal glutenFreePasta = Meal.builder()
//                    .name("Gluten-Free Pasta")
//                    .dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE))
//                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
//                    .build();
//            mealRepository.save(glutenFreePasta);
//            mealIngredientRepository.saveAll(List.of(
//                    MealIngredient.builder()
//                            .meal(glutenFreePasta)
//                            .ingredient(ingredients.get("Pasta"))
//                            .build(),
//                    MealIngredient.builder()
//                            .meal(glutenFreePasta)
//                            .ingredient(ingredients.get("Tomato"))
//                            .build()
//            ));
//
//            Meal kosherBeefStew = Meal.builder()
//                    .name("Kosher Beef Stew")
//                    .dietaryCategories(Set.of(DietaryCategory.KOSHER, DietaryCategory.NON_VEGETARIAN))
//                    .mealTypes(Set.of(MealType.DINNER))
//                    .build();
//            mealRepository.save(kosherBeefStew);
//            mealIngredientRepository.saveAll(List.of(
//                    MealIngredient.builder()
//                            .meal(kosherBeefStew)
//                            .ingredient(ingredients.get("Beef"))
//                            .build(),
//                    MealIngredient.builder()
//                            .meal(kosherBeefStew)
//                            .ingredient(ingredients.get("Tomato"))
//                            .build()
//            ));
//
//            System.out.println("Meals prepopulated.");
//        } else {
//            System.out.println("Meals already exist. Skipping seeding.");
        }




    }
}