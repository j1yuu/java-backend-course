package kkashin.dev;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("kkashin.dev")
public class AppConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Task task() {
        return new Task();
    }

    @Bean
    public TaskManager taskManager(
            ObjectProvider<Task> taskProvider
    ) {
        return new TaskManager(taskProvider);
    }
}
