package kkashin.dev;

import kkashin.dev.config.AppConfig;
import kkashin.dev.model.Course;
import kkashin.dev.model.Student;
import kkashin.dev.util.AllDataGetter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            SessionFactory sessionFactory = context.getBean(SessionFactory.class);
            AllDataGetter allDataGetter = context.getBean(AllDataGetter.class);

            Student student1 = new Student("Jake");
            Student student2 = new Student("Finn");
            Student student3 = new Student("Bubblegum");

            Course course1 = new Course("Math");
            Course course2 = new Course("PE");

            try (Session session = sessionFactory.openSession()) {

                session.beginTransaction();

                session.persist(student1);
                session.persist(student2);
                session.persist(student3);

                session.persist(course1);
                session.persist(course2);

                session.getTransaction().commit();
            }

            System.out.println("------MANY TO MANY------");

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                session
                        .createNativeQuery("insert into student_courses(student_id, course_id) values(:studentId, :courseId)",
                                Void.class
                        )
                                .setParameter("studentId", student1.getId())
                                        .setParameter("courseId", course1.getId())
                                                .executeUpdate();

                session.getTransaction().commit();
            }

            allDataGetter.printAllData();
        }
    }
}
