package kkashin.dev.lessons.model;

public record Book(
        Long id,
        String name,
        String authorName,
        Integer publicationYear,
        Integer pageNumber,
        Integer cost
) {
}
