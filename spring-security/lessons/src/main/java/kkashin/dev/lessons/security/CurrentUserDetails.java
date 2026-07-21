package kkashin.dev.lessons.security;

import kkashin.dev.lessons.model.UserEntity;
import kkashin.dev.lessons.model.dto.auth.UserRole;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CurrentUserDetails(
        Long id,
        String username,
        String email,
        String passwordHash,
        UserRole role
) implements UserDetails {

    public static CurrentUserDetails from(UserEntity user) {
        return new CurrentUserDetails(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
