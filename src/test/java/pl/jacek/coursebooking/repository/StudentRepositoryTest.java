package pl.jacek.coursebooking.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jacek.coursebooking.entity.Course;
import pl.jacek.coursebooking.entity.Teacher;
import pl.jacek.coursebooking.entity.User;
import pl.jacek.coursebooking.enums.CourseAdvancement;

import java.util.List;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveStudent() {
        Teacher teacher = Teacher.builder()
                .email("Tomek@gmail.com")
                .firstName("Tomek")
                .lastName("Kurdej")
                .age(25)
                .courses(null)
                .build();

        Course course = new Course("Java",
                CourseAdvancement.EXPERT,
                teacher);

        User user = User.builder()
                .email("Daniel@gmail.com")
                .firstName("Daniel")
                .lastName("Popuszko")
                .courses(List.of(course))
                .build();

        userRepository.save(user);
    }

    @Test
    public void printAllUsers() {
        List<User> userList =
                userRepository.findAll();

        System.out.println("userList = " + userList);
    }
}