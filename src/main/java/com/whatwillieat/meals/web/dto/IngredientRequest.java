package com.whatwillieat.meals.web.dto;

import com.whatwillieat.meals.model.DietaryCategory;
import jakarta.annotation.Nullable;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IngredientRequest {
    @NotNull
    private String name;

    @Nullable
    private String description;

    @NotNull
    private Set<DietaryCategory> dietaryCategories;
}
