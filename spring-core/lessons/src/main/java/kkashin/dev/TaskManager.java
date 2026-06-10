package kkashin.dev;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import kkashin.dev.aop.Loggable;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class TaskManager {
    private final ObjectProvider<Task> taskProvider;

    public TaskManager(ObjectProvider<Task> taskProvider) {
        this.taskProvider = taskProvider;
    }

    public ObjectProvider<Task> getTaskProvider() {
        return taskProvider;
    }

    @Loggable(value="INFO", times=2)
    public Task printTask() {
        Task task = taskProvider.getObject();
        System.out.println("Current task: " + task.toString());

        return task;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("taskManager postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("taskManager preDestroy");
    }
}
