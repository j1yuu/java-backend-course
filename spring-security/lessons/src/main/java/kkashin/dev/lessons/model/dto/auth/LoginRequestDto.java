package kkashin.dev.lessons.model.dto.auth;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record LoginRequestDto(
        @NotNull
        @Length(min = 3, max = 255)
        String username,
        @NotNull
        @Length(min = 8, max = 255)
        String password
) {
}
