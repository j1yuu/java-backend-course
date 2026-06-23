package kkashin.dev.modulehw.repository.impl;

import kkashin.dev.modulehw.dto.userDtos.CreateUserDto;
import kkashin.dev.modulehw.mappers.UserMapper;
import kkashin.dev.modulehw.model.User;
import kkashin.dev.modulehw.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> userMap;
    private final AtomicLong idCounter = new AtomicLong(0);

    public InMemoryUserRepository() {
        this.userMap = new HashMap<>();
    }

    @Override
    public User save(CreateUserDto createUserDto) {
        User user = UserMapper.mapCreateDtotoUser(createUserDto);
        var id = idCounter.getAndIncrement();

        user.setId(id);
        userMap.put(id, user);

        return user;
    }

    @Override
    public User update(User user) {
        userMap.put(user.getId(), user);

        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public void delete(Long id) {
        userMap.remove(id);
    }
}
