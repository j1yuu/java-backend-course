package kkashin.dev.lessons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record PostsSearchFilterDto(
        @Nullable
        @JsonProperty("page_size")
        Integer pageSize,
        @Nullable
        @JsonProperty("page_offset")
        Integer pageOffset
) {
}
