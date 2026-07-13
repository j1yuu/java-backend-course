package kkashin.dev.exercise4.repository;

import kkashin.dev.exercise4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
