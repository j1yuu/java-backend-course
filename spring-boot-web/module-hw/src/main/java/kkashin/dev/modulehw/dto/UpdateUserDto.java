package kkashin.dev.modulehw.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateUserDto(
    @NotNull
    Long id,

    @Nullable
    @Length(min = 2, max = 36)
    String name,

    @Nullable
    @Email
    String email,

    @Nullable
    @Min(1)
    @Max(100)
    Integer age,

    @Nullable
    List<PetDto> pets
) {
}
