package kkashin.dev;

import kkashin.dev.components.JobRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppContext.class);

        JobRunner runner = context.getBean(JobRunner.class);
        runner.runOnce();
        runner.runOnce();
        runner.runOnce();
    }
}
