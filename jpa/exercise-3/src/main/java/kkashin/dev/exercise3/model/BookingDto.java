package kkashin.dev.exercise3.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookingDto(
        @NotNull
        Long eventId,
        @NotBlank
        @Email
        String customerEmail,
        @NotNull
        @Min(1)
        Integer quantity
) {
}
