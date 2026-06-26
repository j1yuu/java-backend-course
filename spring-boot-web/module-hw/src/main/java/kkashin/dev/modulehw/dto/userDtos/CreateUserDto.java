package kkashin.dev.modulehw.dto.userDtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateUserDto(
        @NotBlank
        @Length(min = 2, max = 36)
        String name,

        @NotBlank
        @Email
        String email,

        @NotNull
        @Min(1)
        @Max(100)
        Integer age
) {
}
