package com.whatwillieat.meals.web.dto;

import com.whatwillieat.meals.model.DietaryCategory;
import com.whatwillieat.meals.model.MealType;
import jakarta.annotation.Nullable;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MealRequest {
    @NotNull
    private String name;

    @Nullable
    private String description;

    @NotNull
    private Set<DietaryCategory> dietaryCategories;

    @NotNull
    private Set<MealType> mealTypes;

    @NotNull
    private LocalDateTime consumedOn;
}
