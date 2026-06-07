package kkashin.dev;

import kkashin.dev.services.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService service = context.getBean(UserService.class);

        service.resetPassword(60L);
        service.updateName(null, "username");
    }
}
