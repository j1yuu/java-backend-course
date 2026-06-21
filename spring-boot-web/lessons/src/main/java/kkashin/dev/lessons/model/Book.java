package kkashin.dev.lessons.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Book(
        @Null
        Long id,

        @Size(max = 30)
        @NotBlank
        String name,

        @Size(max = 30)
        @NotBlank
        @JsonProperty("author_name")
        String authorName,

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
