package kkashin.dev;

import kkashin.dev.config.AppConfig;
import kkashin.dev.model.Post;
import kkashin.dev.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            SessionFactory sessionFactory = context.getBean(SessionFactory.class);

            User user1 = new User("username", null);
            User user2 = new User("username2", null);
            User user3 = new User("username3", null);

            Post post11 = new Post("Title 1", "Content for post 1", user1);
            Post post12 = new Post("Title 2", "Content for post 2", user1);
            Post post13 = new Post("Title 3", "Content for post 3", user1);

            Post post21 = new Post("Title 1", "Content for post 1", user2);
            Post post22 = new Post("Title 2", "Content for post 2", user2);
            Post post23 = new Post("Title 3", "Content for post 3", user2);
            Post post24 = new Post("Title 4", "Content for post 4", user2);


            Post post31 = new Post("Title 1", "Content for post 1", user3);
            Post post32 = new Post("Title 2", "Content for post 2", user3);

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                session.persist(user1);
                session.persist(user2);
                session.persist(user3);

                session.persist(post11);
                session.persist(post12);
                session.persist(post13);

                session.persist(post21);
                session.persist(post22);
                session.persist(post23);
                session.persist(post24);

                session.persist(post31);
                session.persist(post32);

                session.getTransaction().commit();
            }

            System.out.println("\n------------\n");

            try (Session session = sessionFactory.openSession()) {
                List<Post> postList = session.createQuery("select p from Post p join fetch p.author", Post.class).list();

                for (Post post : postList) {
                    System.out.println(post.getAuthor().getName());
                }
            }
        }
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
    }
}
