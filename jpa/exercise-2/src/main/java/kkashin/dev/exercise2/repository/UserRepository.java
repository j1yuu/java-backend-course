package kkashin.dev.exercise2.repository;

import kkashin.dev.exercise2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
