package kkashin.dev.modulehw.dto.userDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kkashin.dev.modulehw.dto.petDtos.PetDto;

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
