package com.whatwillieat.meals.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DietaryCategory> dietaryCategories = new HashSet<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<MealType> mealTypes = new HashSet<>();

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime updatedOn = LocalDateTime.now();

    @Builder.Default
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<MealIngredient> ingredients = new HashSet<>();

    @PreUpdate
    protected void onUpdate() {
        this.updatedOn = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(id, meal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Set<MealIngredient> getIngredients() {
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
        return ingredients;
    }

    public void setIngredients(Set<MealIngredient> ingredients) {
        this.ingredients = new HashSet<>(ingredients); // Ensure it's a mutable copy
    }

    public void addIngredient(MealIngredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
