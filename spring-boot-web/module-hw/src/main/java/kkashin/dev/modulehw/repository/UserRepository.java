package kkashin.dev.modulehw.repository;

import kkashin.dev.modulehw.dto.userDtos.CreateUserDto;
import kkashin.dev.modulehw.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(CreateUserDto createUserDto);
    User update(User user);
    Optional<User> findById(Long id);
    void delete(Long id);
}
