package kkashin.dev.lessons.dto;

import java.time.LocalDateTime;

public record ServerExceptionDto(
        String message,
        String detailedMessage,
        LocalDateTime dateTime
) {
}
