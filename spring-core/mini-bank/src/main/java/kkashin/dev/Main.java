package kkashin.dev;

import kkashin.dev.cli.CliApplication;
import kkashin.dev.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CliApplication cliApplication = context.getBean(CliApplication.class);

        cliApplication.run();
    }
}
