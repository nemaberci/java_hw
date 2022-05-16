package hu.nemaberci.budgetneptun.service;

import hu.nemaberci.budgetneptun.dto.CourseDTO;
import hu.nemaberci.budgetneptun.entity.CourseEntity;
import hu.nemaberci.budgetneptun.repository.CourseRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;
    private final Consumer<CourseEntity> courseEntityChangedCallback;

    public CourseService(CourseRepository courseRepository, UserService userService,
            Consumer<CourseEntity> courseEntityChangedCallback
    ) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.courseEntityChangedCallback = courseEntityChangedCallback;
    }

    public CourseDTO create(String name, String description, Integer capacity) {
        return CourseDTO.fromEntity(
                courseRepository.save(
                        new CourseEntity()
                                .setName(name)
                                .setDescription(description)
                                .setCapacity(capacity)
                                .setExams(new ArrayList<>())
                                .setStudents(new ArrayList<>())
                                .setTeachers(new ArrayList<>())
                )
        );
    }

    public CourseDTO update(
            Long id,
            String name,
            String description,
            Integer capacity
    ) {
        final var course = courseRepository.getById(id);

        if (course.getStudents().size() > capacity) {
            throw new IllegalArgumentException(
                    "Capacity cannot be lower than current student count");
        }

        course.setCapacity(capacity);
        course.setName(name);
        course.setDescription(description);

        courseEntityChangedCallback.accept(course);

        return CourseDTO.fromEntity(courseRepository.save(course));

    }

    public List<CourseDTO> getAll() {
        return courseRepository.findAll()
                .stream().map(CourseDTO::fromEntity).collect(Collectors.toList());
    }

    public CourseDTO getById(Long id) {
        return CourseDTO.fromEntity(
                courseRepository.getById(id)
        );
    }

    private boolean requestByTeacher() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(
                        grantedAuthority -> grantedAuthority.getAuthority()
                                .equals(UserService.roleTeacher)
                );
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public CourseDTO applyToCourse(
            Long courseId,
            Long studentId
    ) {
        final var student = userService.getStudent(studentId);
        final var course = courseRepository.getById(courseId);

        if (course.getStudents().size() + 1 > course.getCapacity() && !requestByTeacher()) {
            throw new IllegalArgumentException("Course full");
        }

        if (course.getStudents().contains(student)) {
            throw new IllegalArgumentException("Student is already in the course");
        }

        course.getStudents()
                .add(student);
        courseEntityChangedCallback.accept(course);

        return CourseDTO.fromEntity(courseRepository.save(course));

    }

    public CourseDTO addTeacherToCourse(
            Long courseId,
            Long teacherId
    ) {
        final var teacher = userService.getTeacher(teacherId);
        final var course = courseRepository.getById(courseId);

        if (course.getTeachers().contains(teacher)) {
            throw new IllegalArgumentException("Teacher is already teaching this course");
        }

        course.getTeachers()
                .add(teacher);
        courseEntityChangedCallback.accept(course);

        return CourseDTO.fromEntity(courseRepository.save(course));

    }

    public CourseDTO removeFromCourse(
            Long courseId,
            Long studentId
    ) {
        final var student = userService.getStudent(studentId);
        final var course = courseRepository.getById(courseId);

        if (!course.getStudents().contains(student)) {
            throw new IllegalArgumentException("Student is not in the course");
        }

        course.getStudents()
                .remove(student);
        courseEntityChangedCallback.accept(course);

        return CourseDTO.fromEntity(courseRepository.save(course));
    }

    public CourseDTO removeTeacherFromCourse(
            Long courseId,
            Long teacherId
    ) {
        final var teacher = userService.getTeacher(teacherId);
        final var course = courseRepository.getById(courseId);

        if (!course.getTeachers().contains(teacher)) {
            throw new IllegalArgumentException("Teacher is not teaching this course");
        }

        course.getTeachers()
                .remove(teacher);
        courseEntityChangedCallback.accept(course);

        return CourseDTO.fromEntity(courseRepository.save(course));

    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    public List<CourseDTO> coursesWithoutStudent(
            Long studentId
    ) {
        final var student = userService.getStudent(studentId);
        return courseRepository.findAll( ).stream()
                .filter(course -> !course.getStudents().contains(student))
                .map(CourseDTO::fromEntity).collect(Collectors.toList());
    }

    public List<CourseDTO> coursesWithStudent(
            Long studentId
    ) {
        final var student = userService.getStudent(studentId);
        return courseRepository.findAll( ).stream()
                .filter(course -> course.getStudents().contains(student))
                .map(CourseDTO::fromEntity).collect(Collectors.toList());
    }

}
