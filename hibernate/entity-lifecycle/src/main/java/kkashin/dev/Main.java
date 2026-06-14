package kkashin.dev;

import kkashin.dev.config.AppConfiguration;
import kkashin.dev.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);

        //transient
        Movie movie = new Movie("Obsession", "Horror", 2026);

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            //persistent
            session.persist(movie);
            session.getTransaction().commit();
        }

        //detached
        movie.setTitle("Not obsession");

        try (Session session = sessionFactory.openSession()) {
            //persistent
            movie = session.merge(movie);

            session.beginTransaction();
            session.persist(movie);
            session.getTransaction().commit();
        }

        //detached

        try (Session session = sessionFactory.openSession()) {
            // persistent
            movie = session.merge(movie);

            session.beginTransaction();
            session.remove(movie);
            session.getTransaction().commit();
        }

        //removed
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
    }
}
