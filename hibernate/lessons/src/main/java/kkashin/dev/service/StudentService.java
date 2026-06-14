package kkashin.dev.service;

import kkashin.dev.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final SessionFactory sessionFactory;

    public StudentService (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Student saveStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(student);
            session.getTransaction().commit();
        }

        return student;
    }

    public void deleteStudent(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.createQuery("delete from Student s where s.id = :id", Void.class)
                    .setParameter("id", id)
                    .executeUpdate();
        }
    }

    public Student getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Student.class, id);
        }
    }

    public List<Student> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select s from Student s", Student.class).list();
        }
    }

    public Student updateStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(student);
            session.getTransaction().commit();

            return student;
        }
    }
}
