package kkashin.dev.exercise4.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateCommentDto(
        @NotNull
        Long userId,
        @NotNull
        Long postId,
        @NotNull
        @NotBlank
        @Length(min = 1, max = 2048)
        String content
) {
}
