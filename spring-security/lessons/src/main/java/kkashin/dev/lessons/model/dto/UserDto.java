package kkashin.dev.lessons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kkashin.dev.lessons.model.dto.auth.UserRole;

import java.time.Instant;
import java.util.List;

public record UserDto(
        Long id,
        String username,
        String email,
        UserRole role,
        List<PostDto> posts,
        @JsonProperty("created_at")
        Instant createdAt
) {
}
