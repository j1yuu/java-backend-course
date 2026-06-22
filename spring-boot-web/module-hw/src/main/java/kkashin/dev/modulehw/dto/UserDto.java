package kkashin.dev.modulehw.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserDto(
        @NotNull
        Long id,
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotNull
        Integer age,
        @NotNull
        List<PetDto> pets
) {
}
