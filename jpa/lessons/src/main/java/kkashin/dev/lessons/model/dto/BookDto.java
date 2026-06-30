package kkashin.dev.lessons.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import kkashin.dev.lessons.model.Author;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookDto(
        @Null
        Long id,

        @Size(max = 30)
        @NotBlank
        String name,

        @Nullable
        @JsonProperty("author_id")
        Long authorId,

        @Min(0)
        @Max(2026)
        @JsonProperty("publication_year")
        Integer publicationYear,

        @Min(1)
        @Max(9999)
        @JsonProperty("page_number")
        Integer pageNumber,

        @Max(999999)
        Integer cost
) {
}
