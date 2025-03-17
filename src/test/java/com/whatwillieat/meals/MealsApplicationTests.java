package com.whatwillieat.meals;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.Ingredient;
import com.whatwillieat.meals.model.Meal;
import com.whatwillieat.meals.repository.IngredientRepository;
import com.whatwillieat.meals.repository.MealIngredientRepository;
import com.whatwillieat.meals.repository.MealRepository;
import com.whatwillieat.meals.configuration.AuthConfig;
import com.whatwillieat.meals.service.AuthenticationService;
import com.whatwillieat.meals.service.IngredientService;
import com.whatwillieat.meals.service.MealService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

	@Mock
	private MealRepository mealRepository;

	@Mock
	private MealIngredientRepository mealIngredientRepository;

	@InjectMocks
	private MealService mealService;

	private Meal meal;
	private UUID mealId;

	@BeforeEach
	void setUp() {
		mealId = UUID.randomUUID();
		meal = new Meal();
		meal.setId(mealId);
	}

	@Test
	void saveMeal() {
		when(mealRepository.save(meal)).thenReturn(meal);
		Meal savedMeal = mealService.saveMeal(meal);
		assertEquals(meal, savedMeal);
	}

	@Test
	void deleteMeal() {
		when(mealRepository.findById(mealId)).thenReturn(Optional.of(meal));
		mealService.delete(mealId);
		verify(mealRepository, times(1)).delete(meal);
	}

	@Test
	void getMeal_NotFound() {
		when(mealRepository.findById(mealId)).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> mealService.getMeal(mealId));
	}
}

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

	@Mock
	private IngredientRepository ingredientRepository;

	@InjectMocks
	private IngredientService ingredientService;

	private Ingredient ingredient;
	private UUID ingredientId;

	@BeforeEach
	void setUp() {
		ingredientId = UUID.randomUUID();
		ingredient = new Ingredient();
		ingredient.setId(ingredientId);
	}

	@Test
	void saveIngredient() {
		when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
		Ingredient savedIngredient = ingredientService.save(ingredient);
		assertEquals(ingredient, savedIngredient);
	}

	@Test
	void deleteIngredient() {
		when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
		ingredientService.delete(ingredientId);
		verify(ingredientRepository, times(1)).delete(ingredient);
	}

	@Test
	void getIngredient_NotFound() {
		when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> ingredientService.getIngredient(ingredientId));
	}
}

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

	@Mock
	private HttpServletRequest request;

	@BeforeEach
	void setUp() {
		AuthConfig.setAuthToken("valid-key"); // Ensure this method exists
	}

	@Test
	void validApiKey() {
		when(request.getHeader("X-API-KEY")).thenReturn("valid-key");
		assertDoesNotThrow(() -> AuthenticationService.getAuthentication(request));
	}

	@Test
	void invalidApiKey() {
		when(request.getHeader("X-API-KEY")).thenReturn("invalid-key");
		assertThrows(BadCredentialsException.class, () -> AuthenticationService.getAuthentication(request));
	}
}