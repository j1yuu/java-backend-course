package kkashin.dev;

import kkashin.dev.model.Group;
import kkashin.dev.model.Profile;
import kkashin.dev.model.Student;
import kkashin.dev.model.Course;
import kkashin.dev.service.GroupService;
import kkashin.dev.service.ProfileService;
import kkashin.dev.service.StudentService;
import kkashin.dev.service.CourseService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext("kkashin.dev")) {

            SessionFactory sessionFactory = context.getBean(SessionFactory.class);

            StudentService studentService = context.getBean(StudentService.class);
            ProfileService profileService = context.getBean(ProfileService.class);
            GroupService groupService = context.getBean(GroupService.class);
            CourseService courseService = context.getBean(CourseService.class);

            Course course1 = new Course("Math-1", "Math");
            Course course2 = new Course("Math-2", "Math");
            Course course3 = new Course("Math-3", "Math");

            Group group1 = groupService.saveGroup("1", 2024L);
            Group group2 = groupService.saveGroup("2", 2025L);
            Group group3 = groupService.saveGroup("3", 2026L);

            courseService.saveCourse(course1);
            courseService.saveCourse(course2);
            courseService.saveCourse(course3);

            Student student1 = new Student("student 1", 22, group1);
            Student student2 = new Student("student 2", 20, group1);

            studentService.saveStudent(student1);
            studentService.saveStudent(student2);

            Profile profile1 = new Profile("My bio", LocalDateTime.now(), student1);

            profileService.saveProfile(profile1);

            groupService.findAll();

            courseService.enrollStudentToCourse(course1.getId(), student1.getId());
            courseService.enrollStudentToCourse(course3.getId(), student1.getId());
        }
    }
}
