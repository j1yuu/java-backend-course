package kkashin.dev.service;

import org.hibernate.SessionFactory;

import kkashin.dev.TransactionHelper;
import kkashin.dev.model.Course;
import kkashin.dev.model.Student;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public CourseService (SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Course saveCourse(Course course) {
        return transactionHelper.executeInTransaction(session -> {
           session.persist(course);
           return course;
        });
    }

    public void enrollStudentToCourse( long courseId, long studentId) {
        transactionHelper.executeInTransaction(session -> {
//           var student = session.find(Student.class, studentId);
//           var course = session.find(Course.class, courseId);
//
//           student.getCourseList().add(course);

            String query = """
                        insert into student_courses (student_id, course_id)
                        values (:studentId, :courseId);
                    """;

            session
                    .createNativeQuery(query, Void.class)
                    .setParameter("studentId", studentId)
                    .setParameter("courseId", courseId).executeUpdate();
        });
    }
}
