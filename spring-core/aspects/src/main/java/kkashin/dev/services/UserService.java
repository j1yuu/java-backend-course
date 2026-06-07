package kkashin.dev.services;

import kkashin.dev.annotations.NotNullArgs;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @NotNullArgs
    public boolean updateName(Long userId, String name) {
        return true;
    }

    @NotNullArgs
    public boolean resetPassword(Long userId) {
        return true;
    }

    public void someAction(String action) {}
}
