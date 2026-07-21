package kkashin.dev.lessons.configuration.jwt;

import io.jsonwebtoken.Jwts;
import kkashin.dev.lessons.security.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenManager {
    private final JwtProperties jwtProperties;

    public String generateToken(String login) {
        return Jwts
                .builder()
                .subject(login)
                .issuer(jwtProperties.issuer())
                .signWith(jwtProperties.secretBase64())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.accessTtl()))
                .compact();
    }

    public String getLogin(String jwt) {
        return Jwts.parser()
                .verifyWith(jwtProperties.secretBase64())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }
}
