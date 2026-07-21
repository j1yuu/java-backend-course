package kkashin.dev.lessons.configuration;

import kkashin.dev.lessons.model.UserEntity;
import kkashin.dev.lessons.model.dto.auth.UserRole;
import kkashin.dev.lessons.repository.UserRepository;
import kkashin.dev.lessons.security.AdminCreditProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements ApplicationRunner {
    private final AdminCreditProperties adminCreditProperties;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (!adminCreditProperties.enabled()) return;

        if (userRepository.existsByUsername(normalize(adminCreditProperties.username())))
            return;

        UserEntity admin = new UserEntity();

        admin.setUsername(adminCreditProperties.username());
        admin.setUsernameNormalized(normalize(adminCreditProperties.username()));
        admin.setEmail(adminCreditProperties.email());
        admin.setEmailNormalized(normalize(adminCreditProperties.email()));
        admin.setRole(UserRole.ADMIN);
        admin.setPasswordHash(passwordEncoder.encode(adminCreditProperties.password()));

        userRepository.save(admin);
    }

    private String normalize(String value) {
        return value.trim().toLowerCase();
    }
}
