package kkashin.dev.config;

import kkashin.dev.model.Course;
import kkashin.dev.model.Student;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@org.springframework.context.annotation.Configuration
@ComponentScan("kkashin.dev")
public class AppConfig {

    @Bean
    public SessionFactory sessionFactory() {
        Configuration configuration = new Configuration();

        configuration
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
                .addPackage("kkashin.dev")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://127.0.0.1:5432/many-to-many")
                .setProperty("hibernate.connection.username", "admin")
                .setProperty("hibernate.connection.password", "test")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create");

        return configuration.buildSessionFactory();
    }
}
