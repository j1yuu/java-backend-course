package kkashin.dev;

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
}
