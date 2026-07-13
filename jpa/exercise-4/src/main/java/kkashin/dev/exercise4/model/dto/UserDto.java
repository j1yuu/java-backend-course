package kkashin.dev.exercise4.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public record UserDto(
        Long id,
        String username,
        String email,
        List<PostDto> posts,
        @JsonProperty("created_at")
        Instant createdAt
) {
}
