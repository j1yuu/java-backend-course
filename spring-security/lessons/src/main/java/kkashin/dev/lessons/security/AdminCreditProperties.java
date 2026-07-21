package kkashin.dev.lessons.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.admin")
public record AdminCreditProperties(
        String username,
        String email,
        String password
) {
    public boolean enabled() {
        return username != null && !username.isBlank()
                && email != null && !email.isBlank()
                && password != null && !password.isBlank();
    }
}
