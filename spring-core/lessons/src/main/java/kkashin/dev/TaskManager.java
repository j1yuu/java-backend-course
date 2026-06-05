package kkashin.dev;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.ObjectProvider;

public class TaskManager {
    private final ObjectProvider<Task> taskProvider;

    public TaskManager(ObjectProvider<Task> taskProvider) {
        this.taskProvider = taskProvider;
    }

    public ObjectProvider<Task> getTaskProvider() {
        return taskProvider;
    }

    public void printTask() {
        System.out.println("Current task: " + taskProvider.getObject().toString());
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
