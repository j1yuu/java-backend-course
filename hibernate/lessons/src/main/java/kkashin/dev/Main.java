package kkashin.dev;

import kkashin.dev.model.Profile;
import kkashin.dev.model.Student;
import kkashin.dev.service.ProfileService;
import kkashin.dev.service.StudentService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext("kkashin.dev")) {

            SessionFactory sessionFactory = context.getBean(SessionFactory.class);

            StudentService studentService = context.getBean(StudentService.class);
            ProfileService profileService = context.getBean(ProfileService.class);

            Student student1 = new Student("student 1", 22);
            Student student2 = new Student("student 2", 20);

            studentService.saveStudent(student1);
            studentService.saveStudent(student2);

            Profile profile1 = new Profile("My bio", LocalDateTime.now(), student1);

            profileService.saveProfile(profile1);
        }
    }
}
