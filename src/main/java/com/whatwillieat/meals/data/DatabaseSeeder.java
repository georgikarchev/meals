package com.whatwillieat.meals.data;

import com.whatwillieat.meals.model.*;
import com.whatwillieat.meals.repository.IngredientRepository;
import com.whatwillieat.meals.repository.MealIngredientRepository;
import com.whatwillieat.meals.service.IngredientService;
import com.whatwillieat.meals.service.MealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.whatwillieat.meals.repository.MealRepository;

@Slf4j
@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final MealRepository mealRepository;
    private final MealIngredientRepository mealIngredientRepository;
    private final MealService mealService;
    private final IngredientService ingredientService;

    @Autowired
    public DatabaseSeeder(MealRepository mealRepository, MealIngredientRepository mealIngredientRepository, MealService mealService, IngredientService ingredientService) {
        this.mealRepository = mealRepository;
        this.mealIngredientRepository = mealIngredientRepository;
        this.mealService = mealService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) {
        if (ingredientService.getIngredientCount() == 0) {
            ingredientService.save(Ingredient.builder().name("Water").dietaryCategories(Set.of(DietaryCategory.VEGAN, DietaryCategory.GLUTEN_FREE)).build());
            ingredientService.save(Ingredient.builder().name("Salt").dietaryCategories(Set.of(DietaryCategory.VEGAN, DietaryCategory.GLUTEN_FREE)).build());
            ingredientService.save(Ingredient.builder().name("Sugar").dietaryCategories(Set.of(DietaryCategory.VEGAN, DietaryCategory.GLUTEN_FREE)).build());

            ingredientService.save(Ingredient.builder().name("Bread").dietaryCategories(Set.of(DietaryCategory.VEGAN)).build());
            ingredientService.save(Ingredient.builder().name("Tomato").dietaryCategories(Set.of(DietaryCategory.VEGAN, DietaryCategory.GLUTEN_FREE)).build());
            ingredientService.save(Ingredient.builder().name("Lettuce").dietaryCategories(Set.of(DietaryCategory.VEGAN, DietaryCategory.GLUTEN_FREE)).build());
            ingredientService.save(Ingredient.builder().name("Pasta").dietaryCategories(Set.of(DietaryCategory.VEGAN)).build());
            ingredientService.save(Ingredient.builder().name("Gluten free Pasta").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.VEGAN)).build());
            ingredientService.save(Ingredient.builder().name("Rice").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.VEGAN)).build());
            ingredientService.save(Ingredient.builder().name("Flour 00").dietaryCategories(Set.of(DietaryCategory.VEGAN)).build());
            ingredientService.save(Ingredient.builder().name("Flour").dietaryCategories(Set.of(DietaryCategory.VEGAN)).build());
            ingredientService.save(Ingredient.builder().name("Rice Flour").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.VEGAN)).build());
            ingredientService.save(Ingredient.builder().name("Potato").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.VEGAN)).build());

            ingredientService.save(Ingredient.builder().name("Mozzarella").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.VEGETARIAN)).build());
            ingredientService.save(Ingredient.builder().name("Cooking Cream").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.VEGETARIAN)).build());
            ingredientService.save(Ingredient.builder().name("Butter").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.VEGETARIAN)).build());
            ingredientService.save(Ingredient.builder().name("Egg").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.VEGETARIAN)).build());

            ingredientService.save(Ingredient.builder().name("Chicken").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.NON_VEGETARIAN)).build());
            ingredientService.save(Ingredient.builder().name("Beef").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.NON_VEGETARIAN)).build());
            ingredientService.save(Ingredient.builder().name("Pork").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.NON_VEGETARIAN)).build());
            ingredientService.save(Ingredient.builder().name("Pork Ham").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.NON_VEGETARIAN)).build());
            ingredientService.save(Ingredient.builder().name("Minced Pork Meat").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.NON_VEGETARIAN)).build());
            ingredientService.save(Ingredient.builder().name("Fish").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.NON_VEGETARIAN)).build());
            ingredientService.save(Ingredient.builder().name("Tuna").dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE, DietaryCategory.NON_VEGETARIAN)).build());

            log.info("Ingredients prepopulated");
        }
        else {
            log.info("Ingredients already exist. Skipping seeding.");
        }

        Map<String, Ingredient> ingredients = ingredientService.getIngredients().stream()
                .collect(Collectors.toMap(Ingredient::getName, ing -> ing));

        if (mealService.getMealsCount() == 0) {
            // 1 Grilled Chicken Salad
            Meal grilledChickenSalad = Meal.builder()
                    .name("Grilled Chicken Salad")
                    .dietaryCategories(Set.of(DietaryCategory.NON_VEGETARIAN))
                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
                    .build();

            mealService.saveMeal(grilledChickenSalad);

            mealService.addIngredientToMeal(
                    grilledChickenSalad,
                    ingredients.get("Chicken"),
                    150.0,
                    UnitOfMeasurement.GRAM);

            // 2 Vegan Buddha Bowl
            Meal veganBuddhaBowl = Meal.builder()
                    .name("Vegan Buddha Bowl")
                    .dietaryCategories(Set.of(DietaryCategory.VEGAN))
                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
                    .build();

            mealService.saveMeal(veganBuddhaBowl);

            mealService.addIngredientToMeal(
                    veganBuddhaBowl,
                    ingredients.get("Tomato"),
                    100.0,
                    UnitOfMeasurement.GRAM
            );
            mealService.addIngredientToMeal(
                    veganBuddhaBowl,
                    ingredients.get("Lettuce"),
                    100.0,
                    UnitOfMeasurement.GRAM
            );

            // 3 Gluten Free Pasta
            Meal glutenFreePasta = Meal.builder()
                    .name("Gluten-Free Pasta")
                    .dietaryCategories(Set.of(DietaryCategory.GLUTEN_FREE))
                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
                    .build();
            mealService.saveMeal(glutenFreePasta);

            mealService.addIngredientToMeal(glutenFreePasta,ingredients.get("Gluten free Pasta"), 250.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(glutenFreePasta,ingredients.get("Tomato"), 10.0, UnitOfMeasurement.GRAM);

            // 4 Pizza Margarita
            Meal pizzaMargarita = Meal.builder()
                    .name("Pizza Margarita")
                    .dietaryCategories(Set.of(DietaryCategory.VEGETARIAN))
                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
                    .build();
            mealService.saveMeal(pizzaMargarita);

            mealService.addIngredientToMeal(pizzaMargarita,ingredients.get("Flour 00"), 250.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(pizzaMargarita,ingredients.get("Tomato"), 100.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(pizzaMargarita,ingredients.get("Mozzarella"), 100.0, UnitOfMeasurement.GRAM);

            // 5 Kosher Beef Stew
            Meal kosherBeefStew = Meal.builder()
                    .name("Kosher Beef Stew")
                    .dietaryCategories(Set.of(DietaryCategory.KOSHER, DietaryCategory.NON_VEGETARIAN))
                    .mealTypes(Set.of(MealType.DINNER))
                    .build();
            mealService.saveMeal(kosherBeefStew);
            mealService.addIngredientToMeal(kosherBeefStew, ingredients.get("Beef"), 150.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(kosherBeefStew, ingredients.get("Tomato"), 200.0, UnitOfMeasurement.GRAM);

            // 6 Musaka
            Meal musaka = Meal.builder()
                    .name("Musaka")
                    .dietaryCategories(Set.of(DietaryCategory.NON_VEGETARIAN))
                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
                    .build();
            mealService.saveMeal(musaka);

            mealService.addIngredientToMeal(musaka,ingredients.get("Flour"), 10.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(musaka,ingredients.get("Tomato"), 100.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(musaka,ingredients.get("Minced Pork Meat"), 200.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(musaka,ingredients.get("Potato"), 200.0, UnitOfMeasurement.GRAM);

            // 7 Pancakes
            Meal pancakes = Meal.builder()
                    .name("Pancakes")
                    .dietaryCategories(Set.of(DietaryCategory.VEGETARIAN))
                    .mealTypes(Set.of(MealType.BREAKFAST, MealType.SNACK))
                    .build();
            mealService.saveMeal(pancakes);

            mealService.addIngredientToMeal(pancakes,ingredients.get("Flour"), 250.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(pancakes,ingredients.get("Egg"), 3.0, UnitOfMeasurement.PIECE);
            mealService.addIngredientToMeal(pancakes,ingredients.get("Butter"), 3.0, UnitOfMeasurement.PIECE);
            mealService.addIngredientToMeal(pancakes,ingredients.get("Sugar"), 3.0, UnitOfMeasurement.PIECE);

            // 8 Pasta Carbonara
            Meal pastaCarbonara = Meal.builder()
                    .name("Pasta Carbonara")
                    .dietaryCategories(Set.of(DietaryCategory.NON_VEGETARIAN))
                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
                    .build();
            mealService.saveMeal(pastaCarbonara);

            mealService.addIngredientToMeal(pastaCarbonara,ingredients.get("Pasta"), 250.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(pastaCarbonara,ingredients.get("Cooking Cream"), 100.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(pastaCarbonara,ingredients.get("Pork Ham"), 150.0, UnitOfMeasurement.GRAM);


            // 9 Pasta with Meat
            Meal pastaWithMeat = Meal.builder()
                    .name("Pasta with Meat")
                    .dietaryCategories(Set.of(DietaryCategory.NON_VEGETARIAN))
                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
                    .build();
            mealService.saveMeal(pastaWithMeat);

            mealService.addIngredientToMeal(pastaWithMeat,ingredients.get("Pasta"), 250.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(pastaWithMeat,ingredients.get("Minced Pork Meat"), 200.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(pastaWithMeat,ingredients.get("Tomato"), 100.0, UnitOfMeasurement.GRAM);

            // 10 Butter Toast
            Meal butterToast = Meal.builder()
                    .name("Butter Toast")
                    .dietaryCategories(Set.of(DietaryCategory.NON_VEGETARIAN))
                    .mealTypes(Set.of(MealType.LUNCH, MealType.DINNER))
                    .build();
            mealService.saveMeal(butterToast);

            mealService.addIngredientToMeal(butterToast,ingredients.get("Bread"), 50.0, UnitOfMeasurement.GRAM);
            mealService.addIngredientToMeal(butterToast,ingredients.get("Butter"), 10.0, UnitOfMeasurement.GRAM);

            log.info("Meals prepopulated");
        } else {
            log.info("Meals already exist. Skipping seeding.");
        }
    }
}