package kkashin.dev.lessons.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
        String issuer,
        Long accessTtl,
        SecretKey secretBase64
) {
}
