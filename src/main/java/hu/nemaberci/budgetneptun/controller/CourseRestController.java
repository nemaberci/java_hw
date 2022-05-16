package hu.nemaberci.budgetneptun.controller;

import hu.nemaberci.budgetneptun.service.CourseService;
import hu.nemaberci.budgetneptun.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
public class CourseRestController {

    private final CourseService courseService;
    private final UserService userService;

    public CourseRestController(CourseService courseService,
            UserService userService
    ) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @PostMapping("/student/apply")
    public ResponseEntity<Boolean> apply(@RequestParam Long id) {
        courseService.applyToCourse(
                id,
                userService.currentUserId()
        );
        return ResponseEntity.ok(true);
    }

    @PostMapping("/teacher/apply")
    public ResponseEntity<Boolean> teacherApply(@RequestParam Long id) {
        courseService.addTeacherToCourse(
                id,
                userService.currentUserId()
        );
        return ResponseEntity.ok(true);
    }

    @PostMapping("/student/leave")
    public ResponseEntity<Boolean> leave(@RequestParam Long id) {
        courseService.removeFromCourse(
                id,
                userService.currentUserId()
        );
        return ResponseEntity.ok(true);
    }

    @PostMapping("/teacher/leave")
    public ResponseEntity<Boolean> teacherLeave(@RequestParam Long id) {
        courseService.removeTeacherFromCourse(
                id,
                userService.currentUserId()
        );
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.ok(true);
    }

}
