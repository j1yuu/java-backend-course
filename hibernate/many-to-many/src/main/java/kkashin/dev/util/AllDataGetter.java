package kkashin.dev.util;

import kkashin.dev.model.Course;
import kkashin.dev.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class AllDataGetter {
    private final SessionFactory sessionFactory;

    public AllDataGetter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void printAllData() {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("\n------Students------");
            session
                    .createQuery("select s from Student s left join fetch s.courseList", Student.class)
                    .list()
                    .forEach(System.out::println);

            System.out.println("\n------COURSES------");
            session
                    .createQuery("select c from Course c", Course.class)
                    .list()
                    .forEach(System.out::println);

            System.out.println("\n------STUDENT TO COURSE------");
            session.createNativeQuery("select * from student_courses").list().forEach(System.out::println);
        }
    }
}