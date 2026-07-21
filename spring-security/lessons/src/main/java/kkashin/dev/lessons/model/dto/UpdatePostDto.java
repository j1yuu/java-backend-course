package kkashin.dev.lessons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import kkashin.dev.lessons.model.PostStatus;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record UpdatePostDto(
        @NotNull
        Long id,
        @NotNull
        Long userId,
        @Nullable
        @Length(min = 2, max = 64)
        String title,
        @Nullable
        @JsonProperty("status")
        PostStatus status,
        @Nullable
        @Length(min = 2, max = 2048)
        String content,
        @Nullable
        List<String> categories
) {
}
