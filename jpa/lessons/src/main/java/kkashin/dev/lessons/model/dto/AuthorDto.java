package kkashin.dev.lessons.model.dto;

import java.util.List;

public record AuthorDto(
        Long id,
        String name,
        Integer birthYear,
        List<BookDto> books
) {
}
