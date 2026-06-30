package kkashin.dev.lessons.model;

public record Book(
        Long id,
        String name,
        Author author,
        Integer publicationYear,
        Integer pageNumber,
        Integer cost
) {
}
