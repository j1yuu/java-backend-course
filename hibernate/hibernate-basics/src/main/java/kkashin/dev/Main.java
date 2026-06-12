package kkashin.dev;

import kkashin.dev.config.HibernateConfig;
import kkashin.dev.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("kkashin.dev")) {
            SessionFactory sessionFactory = context.getBean(SessionFactory.class);

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Movie movie1 = new Movie("Programming for beginners", "Horror", 2026);
                Movie movie2 = new Movie("Java Backend", "Crime", 2025);
                Movie movie3 = new Movie("Survival in Siberia", "Comedy", 2030);

                session.persist(movie1);
                session.persist(movie2);
                session.persist(movie3);

                session.getTransaction().commit();
            }

            try (Session session = sessionFactory.openSession()) {
                List<Movie> movies = session.createQuery("from Movie", Movie.class).getResultList();

                for (Movie movie : movies) {
                    System.out.println(
                            "Movie " + movie.getId() + ": "
                                    + movie.getTitle() + ", "
                                    + movie.getGenre() + ", "
                                    + movie.getReleaseYear()
                    );
                }

                Movie horrorMovie = session
                        .createQuery("from Movie m where m.genre = :genre", Movie.class)
                        .setParameter("genre", "Horror")
                        .getSingleResult();

                System.out.println("Horror movie: " + horrorMovie.getTitle());
            }

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Movie movieToUpdate = session.find(Movie.class, 1L);
                movieToUpdate.setTitle("Nowadays programming");

                session.getTransaction().commit();

                session.beginTransaction();

                session
                        .createQuery("delete from Movie m where m.id = :id")
                        .setParameter("id", 3L)
                        .executeUpdate();

                session.getTransaction().commit();
            }

            try (Session session = sessionFactory.openSession()) {
                List<Movie> movies = session.createQuery("from Movie", Movie.class).getResultList();

                for (Movie movie : movies) {
                    System.out.println(
                            "Movie " + movie.getId() + ": "
                                    + movie.getTitle() + ", "
                                    + movie.getGenre() + ", "
                                    + movie.getReleaseYear()
                    );
                }
            }
        }
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
    }
}
