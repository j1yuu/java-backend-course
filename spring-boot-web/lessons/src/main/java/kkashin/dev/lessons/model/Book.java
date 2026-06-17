package kkashin.dev.lessons.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Book(
        Long id,
        String name,

        @JsonProperty("author_name")
        String authorName,

        @JsonProperty("publication_year")
        Integer publicationYear,

        @JsonProperty("page_number")
        Integer pageNumber,

        Integer cost
) {
}
