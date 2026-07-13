package kkashin.dev.exercise4.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CountByCategoryDto(
        @NotNull
        @NotBlank
        String category,
        @NotNull
        @Min(0)
        Long count
) {
}
