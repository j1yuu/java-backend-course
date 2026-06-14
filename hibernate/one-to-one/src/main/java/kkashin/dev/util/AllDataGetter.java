package kkashin.dev.util;

import kkashin.dev.model.Profile;
import kkashin.dev.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllDataGetter {
    private final SessionFactory sessionFactory;

    public AllDataGetter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void printAllData() {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("------USERS------");
            session
                    .createQuery("select u from User u", User.class)
                    .list()
                    .forEach(System.out::println);

            System.out.println("------PROFILES------");
            session
                    .createQuery("select p from Profile p", Profile.class)
                    .list()
                    .forEach(System.out::println);
        }
    }
}
