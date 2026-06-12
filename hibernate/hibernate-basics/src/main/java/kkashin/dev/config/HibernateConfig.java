package kkashin.dev.config;

import kkashin.dev.model.Movie;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration
                .addAnnotatedClass(Movie.class)
                .addPackage("kkashin.dev")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://127.0.0.1:5432/hibernate-core")
                .setProperty("hibernate.connection.username", "admin")
                .setProperty("hibernate.connection.password", "test")
                .setProperty("hibernate.hbm2ddl.auto", "create-drop");

        return configuration.buildSessionFactory();
    }
}
