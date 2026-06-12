package kkashin.dev;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        Configuration configuration = new Configuration();

        configuration
                .addAnnotatedClass(Student.class)
                .addPackage("kkashin.dev")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://127.0.0.1:5432/hibernate-lessons")
                .setProperty("hibernate.connection.username", "admin")
                .setProperty("hibernate.connection.password", "test")
//                .setProperty("hibernate.show_sql", "true")
//                .setProperty("hibernate.format_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create-drop");

        return configuration.buildSessionFactory();
    }
}
