package com.whatwillieat.meals.web.dto;

import com.whatwillieat.meals.model.UnitOfMeasurement;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddIngredientRequest {
    private UUID ingredientId;
    private double quantity;
    private UnitOfMeasurement unitOfMeasurement;
}

