package kkashin.dev.lessons.repository;

import kkashin.dev.lessons.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
    select u from User u where u.emailNormalized = :email
""")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query("""
    select u from User u where u.usernameNormalized = :username
""")
    Optional<UserEntity> findByUsername(@Param("username") String username);

    @Query("""
    select exists (
        select 1 from User u where u.emailNormalized = :email
    )
""")
    boolean existsByEmail(@Param("email") String email);

    @Query("""
    select exists (
        select 1 from User u where u.usernameNormalized = :username
    )
""")
    boolean existsByUsername(@Param("username") String username);
}
