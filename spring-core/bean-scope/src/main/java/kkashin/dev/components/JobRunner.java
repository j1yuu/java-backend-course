package kkashin.dev.components;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

@Component
public class JobRunner {
//    private final TaskContext taskContext;
    private final ObjectProvider<TaskContext> taskProvider;

/*
    public JobRunner (TaskContext taskContext) {
        this.taskContext = taskContext;
    }
*/
    public JobRunner(ObjectProvider<TaskContext> taskProvider) {
        this.taskProvider = taskProvider;
    }

//    public TaskContext getTaskContext() {
//        return taskContext;
//    }

    public void runOnce() {
//        System.out.println("Task id: " + taskContext.getId());
        System.out.println("Task id: " + taskProvider.getObject().getId());
    }
}
