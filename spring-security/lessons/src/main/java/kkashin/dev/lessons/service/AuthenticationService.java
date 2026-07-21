package kkashin.dev.lessons.service;

import kkashin.dev.lessons.configuration.jwt.JwtTokenManager;
import kkashin.dev.lessons.model.UserEntity;
import kkashin.dev.lessons.model.dto.auth.LoginRequestDto;
import kkashin.dev.lessons.security.CurrentUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;

    public String authenticateUser(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password()
                )
        );

        return jwtTokenManager.generateToken(loginRequestDto.username());
    }

    public CurrentUserDetails getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new IllegalStateException("Authentication not present");

        }
        return (CurrentUserDetails) authentication.getPrincipal();
    }
}
