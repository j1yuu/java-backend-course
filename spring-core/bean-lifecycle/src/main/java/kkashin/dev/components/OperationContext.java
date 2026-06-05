package kkashin.dev.components;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OperationContext {
    public OperationContext() {
        System.out.println("[OperationContext] bean created");
    }

    @PostConstruct
    public void init() {
        System.out.println("[OperationContext] initialized");
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("[OperationContext] destroyed");
    }
}
