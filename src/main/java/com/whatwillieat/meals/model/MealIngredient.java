package com.whatwillieat.meals.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealIngredient {
    @EmbeddedId
    private MealIngredientId id;

    @ManyToOne
    @JsonIgnore
    @MapsId("meal")
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne
    @MapsId("ingredient")
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(nullable = false)
    private double quantity;

    @Enumerated(EnumType.STRING)
    private UnitOfMeasurement unitOfMeasurement;

    public MealIngredient(Meal meal, Ingredient ingredient, double quantity, UnitOfMeasurement unitOfMeasurement) {
        this.id = new MealIngredientId(meal.getId(), ingredient.getId());
        this.meal = meal;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unitOfMeasurement = unitOfMeasurement;
    }
}
