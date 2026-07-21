package kkashin.dev.lessons.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kkashin.dev.lessons.model.UserEntity;
import kkashin.dev.lessons.model.dto.UserDto;
import kkashin.dev.lessons.model.dto.auth.RegisterRequestDto;
import kkashin.dev.lessons.repository.PostRepository;
import kkashin.dev.lessons.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor

public class UserService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserEntity findByLogin(@NonNull String login) {
        UserEntity user;

        if (login.contains("@")) {
            user = userRepository.findByEmail(login.trim().toLowerCase()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
        } else {
            user = userRepository.findByUsername(login.trim().toLowerCase()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
        }

        return user;
    }

    @Transactional
    public UserDto registerUser(@Valid RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.email())) {
            throw new IllegalArgumentException("User with this credentials already exists");
        }
        if (userRepository.existsByUsername(registerRequestDto.username())) {
            throw new IllegalArgumentException("User with this credentials already exists");
        }

        var hashedPassword = passwordEncoder.encode(registerRequestDto.password());
        var userToSave = new UserEntity(registerRequestDto, hashedPassword);

        var user = userRepository.save(userToSave);
        return UserEntity.toDto(user, List.of());
    }
}
