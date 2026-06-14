package kkashin.dev.config;

import kkashin.dev.model.Post;
import kkashin.dev.model.User;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("kkashin.dev")
public class AppConfig {
    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Post.class)
                .addPackage("kkashin.dev")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://127.0.0.1:5432/many-to-one")
                .setProperty("hibernate.connection.username", "admin")
                .setProperty("hibernate.connection.password", "test")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create");

        return configuration.buildSessionFactory();
    }
}
