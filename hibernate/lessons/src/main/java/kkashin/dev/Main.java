package kkashin.dev;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext("kkashin.dev")) {

            SessionFactory sessionFactory = context.getBean(SessionFactory.class);

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Student student1 = new Student("Student", 22);
                Student student2 = new Student("Student2", 23);
                Student student3 = new Student("null", 21);

                session.persist(student1);
                session.persist(student2);
                session.persist(student3);

                session.getTransaction().commit();

                Student studentById1 = session.find(Student.class, 1L);
                System.out.println("Student1: " + studentById1.getName());

                Student studentById2 = session.createQuery(
                        "SELECT s FROM Student s where s.id = :id", Student.class
                )
                        .setParameter("id", 2)
                        .getSingleResult();

                System.out.println("STUDENT2: " + student2.getName());

                session.beginTransaction();

//                Student studentForUpdate = session.find(Student.class, 1L);
//                studentForUpdate.setAge(30);
//                studentForUpdate.setName("Mimi");
//
//                session.getTransaction().commit();
//
//                System.out.println(session.find(Student.class, 1L).getName());

                Student studentForDelete = session.find(Student.class, 2L);
//                session.remove(studentForDelete);
//                session
//                        .createQuery(
//                        "DELETE FROM Student s where s.id = 2"
//                )
//                        .executeUpdate();
                session.createNativeQuery(
                        "delete from students s where s.id = 2", Void.class
                ).executeUpdate();

                session.getTransaction().commit();

            }
        }
    }
}
