package kkashin.dev.exercise4.service;

import kkashin.dev.exercise4.model.Post;
import kkashin.dev.exercise4.model.dto.CreatePostDto;
import kkashin.dev.exercise4.model.dto.PostDto;
import kkashin.dev.exercise4.repository.PostRepository;
import kkashin.dev.exercise4.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {
    private PostRepository postRepository;
    private UserRepository userRepository;


}
