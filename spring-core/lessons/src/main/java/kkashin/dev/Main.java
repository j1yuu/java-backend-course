package kkashin.dev;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        TaskProperties taskProperties = context.getBean(TaskProperties.class);
        System.out.println(taskProperties);

//        TaskManager taskManager = context.getBean(TaskManager.class);

//        taskManager.printTask();
//        taskManager.printTask();
//        taskManager.printTask();

        context.close();
    }
}
