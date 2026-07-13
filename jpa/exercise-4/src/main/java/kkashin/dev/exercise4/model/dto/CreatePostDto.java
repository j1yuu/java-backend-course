package kkashin.dev.exercise4.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CreatePostDto(
        @JsonProperty("user_id")
        @NotNull
        Long userId,
        @NotNull
        @NotBlank
        @Length(min = 2, max = 64)
        String title,
        @NotNull
        @NotBlank
        @Length(min = 2, max = 2048)
        String content,
        @Nullable
        List<String> categories
) {
}
