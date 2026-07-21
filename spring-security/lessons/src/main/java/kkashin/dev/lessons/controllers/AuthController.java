package kkashin.dev.lessons.controllers;

import jakarta.validation.Valid;
import kkashin.dev.lessons.model.dto.UserDto;
import kkashin.dev.lessons.model.dto.auth.JwtDto;
import kkashin.dev.lessons.model.dto.auth.LoginRequestDto;
import kkashin.dev.lessons.model.dto.auth.RegisterRequestDto;
import kkashin.dev.lessons.service.AuthenticationService;
import kkashin.dev.lessons.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public AuthController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(
            @RequestBody @Valid RegisterRequestDto registerRequestDto
            ) {
        var user = userService.registerUser(registerRequestDto);

        return ResponseEntity.created(URI.create("/users/" + user.id())).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> loginUser(
            @RequestBody @Valid LoginRequestDto loginRequestDto
            ) {
        var token = authenticationService.authenticateUser(loginRequestDto);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body(new JwtDto(token));
    }
}
