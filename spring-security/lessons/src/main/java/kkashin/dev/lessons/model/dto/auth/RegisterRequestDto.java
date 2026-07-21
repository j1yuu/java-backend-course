package kkashin.dev.lessons.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegisterRequestDto(
        @NotNull
        @Length(min = 3, max = 255)
        String username,
        @NotNull
        @Email
        String email,
        @NotNull
        @Length(min = 8, max = 255)
        String password
) {
}
