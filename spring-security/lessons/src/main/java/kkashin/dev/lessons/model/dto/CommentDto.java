package kkashin.dev.lessons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

public record CommentDto(
        @NotNull
        Long id,
        @NotNull
        @NotBlank
        @Length(min = 1, max = 2048)
        String content,
        @NotNull
        @JsonProperty("user_id")
        Long userId,
        @NotNull
        @JsonProperty("post_id")
        Long postId,
        @NotNull
        @JsonProperty("created_at")
        Instant createdAt,
        @NotNull
        @JsonProperty("updated_at")
        Instant updatedAt
) {
}
