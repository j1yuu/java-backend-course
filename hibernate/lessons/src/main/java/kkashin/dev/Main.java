package kkashin.dev;

import kkashin.dev.service.StudentService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext("kkashin.dev")) {

            SessionFactory sessionFactory = context.getBean(SessionFactory.class);

            StudentService studentService = context.getBean(StudentService.class);

            Student student1 = new Student("student 1", 22);
            Student student2 = new Student("student 2", 20);

            studentService.saveStudent(student1);
            studentService.saveStudent(student2);
        }
    }
}
