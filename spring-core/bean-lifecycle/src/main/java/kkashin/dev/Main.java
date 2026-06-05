package kkashin.dev;

import kkashin.dev.components.AppLogger;
import kkashin.dev.components.OperationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        AppLogger logger1 = context.getBean(AppLogger.class);
        AppLogger logger2 = context.getBean(AppLogger.class);

        System.out.println(logger1 == logger2);

        OperationContext ctx1 = context.getBean(OperationContext.class);
        OperationContext ctx2 = context.getBean(OperationContext.class);
        OperationContext ctx3 = context.getBean(OperationContext.class);

        System.out.println(ctx1 == ctx2);

        context.close();
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
    }
}
