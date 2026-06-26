package kkashin.dev.modulehw.dto.petDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetDto(
        @NotNull
        Long id,
        @NotBlank
        String name,
        @NotNull
        Long userId
) {
}
