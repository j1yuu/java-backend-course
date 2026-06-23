package kkashin.dev.modulehw.controller;

import jakarta.validation.Valid;
import kkashin.dev.modulehw.dto.userDtos.CreateUserDto;
import kkashin.dev.modulehw.dto.userDtos.UpdateUserDto;
import kkashin.dev.modulehw.dto.userDtos.UserDto;
import kkashin.dev.modulehw.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        logger.info("Post request for createUser");

        var user = userService.save(createUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto) {
        logger.info("Update request for updateUser");

        var user = userService.update(updateUserDto);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Delete request for deleteUser");

        userService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
        logger.info("Get request for findUserById");

        var user = userService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
