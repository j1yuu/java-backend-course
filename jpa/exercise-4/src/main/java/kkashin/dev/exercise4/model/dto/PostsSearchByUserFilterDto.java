package kkashin.dev.exercise4.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record PostsSearchByUserFilterDto(
        @Nullable
        @JsonProperty("page_size")
        Integer pageSize,
        @Nullable
        @JsonProperty("page_offset")
        Integer pageOffset,
        @NotNull
        @JsonProperty("user_id")
        Long userId
) {
}
