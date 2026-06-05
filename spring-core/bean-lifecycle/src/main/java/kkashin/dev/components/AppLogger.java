package kkashin.dev.components;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class AppLogger {
    public AppLogger() {
        System.out.println("[AppLogger] created bean");
    }

    @PostConstruct
    public void init() {
        System.out.println("[AppLogger] initialized");
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("[AppLogger] destroyed");
    }
}
