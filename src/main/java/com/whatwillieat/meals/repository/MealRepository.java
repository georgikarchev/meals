package com.whatwillieat.meals.repository;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MealRepository extends JpaRepository<Meal, UUID> {
    List<Meal> findByType(DietaryCategory type);
}
