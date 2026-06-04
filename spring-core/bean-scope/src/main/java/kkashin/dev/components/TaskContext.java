package kkashin.dev.components;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskContext {
    private final String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }
}
