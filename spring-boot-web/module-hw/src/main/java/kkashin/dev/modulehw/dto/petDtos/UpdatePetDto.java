package kkashin.dev.modulehw.dto.petDtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdatePetDto(
        @NotNull
        Long id,
        @Nullable
        @Length(min = 2, max = 36)
        String name,
        @Nullable
        Long userId
) {
}
