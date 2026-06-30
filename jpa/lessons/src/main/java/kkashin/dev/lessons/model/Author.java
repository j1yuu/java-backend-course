package kkashin.dev.lessons.model;

import java.util.List;

public record Author(
        Long id,
        String name,
        Integer birthYear,
        List<Book> books
) {
}
