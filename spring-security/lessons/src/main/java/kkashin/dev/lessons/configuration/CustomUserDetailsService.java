package kkashin.dev.lessons.configuration;

import kkashin.dev.lessons.model.UserEntity;
import kkashin.dev.lessons.repository.UserRepository;
import lombok.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user;
        if (username.contains("@")) {
            user = userRepository.findByEmail(username.trim().toLowerCase()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
        } else {
            user = userRepository.findByUsername(username.trim().toLowerCase()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
        }

        return User.withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(user.getRole().toString())
                .build();
    }
}
