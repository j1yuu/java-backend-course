package kkashin.dev.lessons.controllers;

import jakarta.validation.Valid;
import kkashin.dev.lessons.model.dto.PostDto;
import kkashin.dev.lessons.model.dto.PostsSearchByUserFilterDto;
import kkashin.dev.lessons.model.dto.PostsSearchFilterDto;
import kkashin.dev.lessons.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/all")
    public List<PostDto> getAllPosts(@RequestParam @Valid PostsSearchFilterDto postsSearchFilterDto) {
        return postService.findAllPosts(postsSearchFilterDto);
    }

    @GetMapping("/all/{id}")
    public List<PostDto> getAllUserPosts(
            @PathVariable @Valid Long id,
            @RequestParam @Valid PostsSearchFilterDto postsSearchFilterDto
    ) {
        var postsSearchFilterWithUserIdDto = new PostsSearchByUserFilterDto(
                postsSearchFilterDto.pageSize(),
                postsSearchFilterDto.pageOffset(),
                id
        );

        return postService.findUserPosts(postsSearchFilterWithUserIdDto);
    }
}
