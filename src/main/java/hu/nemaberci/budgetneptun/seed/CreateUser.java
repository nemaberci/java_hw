package hu.nemaberci.budgetneptun.seed;

import hu.nemaberci.budgetneptun.entity.CourseEntity;
import hu.nemaberci.budgetneptun.entity.ExamEntity;
import hu.nemaberci.budgetneptun.entity.UserEntity;
import hu.nemaberci.budgetneptun.repository.CourseRepository;
import hu.nemaberci.budgetneptun.repository.ExamRepository;
import hu.nemaberci.budgetneptun.repository.ExamResultRepository;
import hu.nemaberci.budgetneptun.repository.UserRepository;
import hu.nemaberci.budgetneptun.service.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public CreateUser(UserRepository userRepository,
            CourseRepository courseRepository,
            ExamRepository examRepository,
            ExamResultRepository examResultRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.examRepository = examRepository;
        this.examResultRepository = examResultRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void seed() {

        final var users = userRepository.saveAll(
                List.of(
                        new UserEntity()
                                .setNeptunCode("QWE123")
                                .setPasswordHash(passwordEncoder.encode("password"))
                                .setRoles(List.of("ROLE_ADMIN", UserService.roleTeacher,
                                        UserService.roleStudent
                                )),
                        new UserEntity()
                                .setNeptunCode("QWE124")
                                .setPasswordHash(passwordEncoder.encode("password"))
                                .setRoles(List.of("ROLE_ADMIN", UserService.roleTeacher,
                                        UserService.roleStudent
                                )),
                        new UserEntity()
                                .setNeptunCode("QWE125")
                                .setPasswordHash(passwordEncoder.encode("password"))
                                .setRoles(List.of("ROLE_ADMIN", UserService.roleStudent)),
                        new UserEntity()
                                .setNeptunCode("QWE126")
                                .setPasswordHash(passwordEncoder.encode("password"))
                                .setRoles(List.of("ROLE_ADMIN", UserService.roleStudent)),
                        new UserEntity()
                                .setNeptunCode("QWE127")
                                .setPasswordHash(passwordEncoder.encode("password"))
                                .setRoles(List.of("ROLE_ADMIN", UserService.roleStudent)),
                        new UserEntity()
                                .setNeptunCode("QWE128")
                                .setPasswordHash(passwordEncoder.encode("password"))
                                .setRoles(List.of("ROLE_ADMIN", UserService.roleTeacher))
                        )
        );

        final var courses = courseRepository.saveAll(
                List.of(
                        new CourseEntity()
                                .setName("test1")
                                .setDescription("desc1")
                                .setTeachers(new ArrayList<>())
                                .setExams(new ArrayList<>())
                                .setStudents(new ArrayList<>())
                                .setCapacity(2),
                        new CourseEntity()
                                .setName("test2")
                                .setDescription("desc2")
                                .setTeachers(new ArrayList<>())
                                .setExams(new ArrayList<>())
                                .setStudents(new ArrayList<>())
                                .setCapacity(3),
                        new CourseEntity()
                                .setName("test3")
                                .setDescription("desc3")
                                .setTeachers(new ArrayList<>())
                                .setExams(new ArrayList<>())
                                .setStudents(new ArrayList<>())
                                .setCapacity(3)
                )
        );

        final var exams = examRepository.saveAll(
                List.of(
                        new ExamEntity()
                                .setCapacity(1)
                                .setTime(LocalDateTime.now().plusDays(1))
                                .setAttendants(new ArrayList<>())
                                .setDescription("Casual exam")
                                .setCourse(courses.get(0))
                )
        );

        courses.get(0).getTeachers().add(
                users.get(0)
        );
        courses.get(0).getStudents().add(
                users.get(0)
        );

        courseRepository.saveAll(courses);

    }

}
