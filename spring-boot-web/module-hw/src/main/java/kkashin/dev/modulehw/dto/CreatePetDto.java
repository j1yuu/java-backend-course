package kkashin.dev.modulehw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreatePetDto(
        @NotBlank
        @Length(min = 2, max = 36)
        String name,
        @NotNull
        Long userId
) {
}
