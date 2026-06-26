package kkashin.dev.exercise1.model.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record CreateEmployeeDto(
        @NotBlank
        @Length(min = 2, max = 36)
        String firstName,
        @NotBlank
        @Length(min = 2, max = 36)
        String lastName,
        @NotNull
        @Email
        String email,
        @NotBlank
        @Length(min = 2, max = 128)
        String department,
        @NotNull
        @Min(0)
        Double salary,
        @NotNull
        LocalDateTime hireDate
) {
}
