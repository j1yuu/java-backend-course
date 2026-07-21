package kkashin.dev.lessons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kkashin.dev.lessons.model.PostStatus;

import java.time.Instant;
import java.util.List;

public record PostDto(
        Long id,
        String title,
        String content,
        PostStatus status,
        List<String> categories,
        List<CommentDto> comments,
        @JsonProperty("user_id")
        Long userId,
        @JsonProperty("created_at")
        Instant createdAt,
        @JsonProperty("updatedAt")
        Instant updatedAt
) {
}
