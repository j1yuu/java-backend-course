package kkashin.dev.lessons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kkashin.dev.lessons.model.dto.UserDto;
import kkashin.dev.lessons.model.dto.auth.RegisterRequestDto;
import kkashin.dev.lessons.model.dto.auth.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_users_username",
                        columnNames = "username_norm"
                ),
                @UniqueConstraint(
                        name = "uk_users_email",
                        columnNames = "email_norm"
                )
        }
)
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "username", nullable = false, length = 36)
    private String username;

    @NotBlank
    @Column(name = "username_norm", nullable = false, length = 36)
    private String usernameNormalized;

    @NotBlank
    @Email
    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @NotBlank
    @Email
    @Column(name = "email_norm", nullable = false, length = 256)
    private String emailNormalized;

    @NotBlank
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Column(name = "role", nullable = false, length = 32)
    private UserRole role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    public UserEntity(RegisterRequestDto registerRequestDto, String passwordHash) {
        this.username = registerRequestDto.username();
        this.usernameNormalized = registerRequestDto.username().trim().toLowerCase();
        this.email = registerRequestDto.email();
        this.emailNormalized = registerRequestDto.email().trim().toLowerCase();
        this.role = UserRole.USER;
        this.passwordHash = passwordHash;
        this.posts = new ArrayList<>();
    }

    public static UserDto toDto(UserEntity userEntity, List<Post> posts) {
        return new UserDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getRole(),
                posts.stream().map(Post::toDto).toList(),
                userEntity.getCreatedAt()
        );
    }
}
