package kkashin.dev.exercise1.model.domain;

import java.time.LocalDateTime;

public record Employee(
        Long id,
        String firstName,
        String lastName,
        String email,
        String department,
        Double salary,
        LocalDateTime hireDate
) {
}
