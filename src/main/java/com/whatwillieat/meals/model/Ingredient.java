package com.whatwillieat.meals.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DietaryCategory> dietaryCategories;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime updatedOn = LocalDateTime.now();

    @Builder.Default
    private boolean isDeleted = false;

    @PreUpdate
    protected void onUpdate() {
        this.updatedOn = LocalDateTime.now();
    }
}
