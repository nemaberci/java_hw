package hu.nemaberci.budgetneptun.controller;

import hu.nemaberci.budgetneptun.dto.CourseDTO;
import hu.nemaberci.budgetneptun.dto.UserDTO;
import hu.nemaberci.budgetneptun.service.CourseService;
import hu.nemaberci.budgetneptun.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;

    public CourseController(CourseService courseService,
            UserService userService
    ) {
        this.courseService = courseService;
        this.userService = userService;
    }

    private void addModelAttributes(Model model) {
        model.addAttribute("userId", userService.currentUserId());
        model.addAttribute("courses", courseService.getAll());
    }

    @GetMapping("/list")
    public String list(Model model) {
        this.addModelAttributes(model);
        return "course/courses";
    }

    @GetMapping("/mine")
    public String myCourses(Model model) {
        this.addModelAttributes(model);
        return "course/courses";
    }

    @GetMapping("/apply")
    public String notMyCourses(Model model) {
        this.addModelAttributes(model);
        return "course/courses";
    }

    @GetMapping("/update/{id}")
    public String updateCourse(Model model, @PathVariable Long id) {
        model.addAttribute("course", courseService.getById(id));
        return "course/course";
    }

    @GetMapping("/new")
    public String newCourse(Model model) {
        return "course/course";
    }

    @PostMapping("/new")
    public String newCourse(@RequestParam Map<String, String> body) {
        courseService.create(
                body.get("name"),
                body.get("description"),
                Integer.parseInt(body.get("capacity"))
        );
        return "redirect:/course/list";
    }

    @GetMapping("/addstudent/{id}")
    public String addStudent(@PathVariable Long id, Model model) {
        var course = courseService.getById(id);
        var students = userService.getStudents().stream()
                .filter(user -> course.getStudentIds().stream().noneMatch(studentId -> user.getId().equals(studentId)))
                .collect(Collectors.toList());
        model.addAttribute("users", students);
        model.addAttribute("course", course);
        model.addAttribute("remove", false);
        return "course/add_to_course";
    }

    @GetMapping("/removestudent/{id}")
    public String removeStudent(@PathVariable Long id, Model model) {
        var course = courseService.getById(id);
        var students = userService.getStudents().stream()
                .filter(user -> course.getStudentIds().stream().anyMatch(studentId -> user.getId().equals(studentId)))
                .collect(Collectors.toList());
        model.addAttribute("users", students);
        model.addAttribute("course", course);
        model.addAttribute("remove", true);
        return "course/add_to_course";
    }

    @PostMapping("/addstudent/{id}")
    public String addStudentPost(@PathVariable Long id, @RequestParam Long student) {
        courseService.applyToCourse(
                id,
                student
        );
        return "redirect:/course/list";
    }

    @PostMapping("/removestudent/{id}")
    public String removeStudentPost(@PathVariable Long id, @RequestParam Long student) {
        courseService.removeFromCourse(
                id,
                student
        );
        return "redirect:/course/list";
    }

    @PostMapping("/update/{id}")
    public String updateOnPost(@RequestParam Map<String, String> body, @PathVariable Long id) {
        courseService.update(
                id,
                body.get("name"),
                body.get("description"),
                Integer.parseInt(body.get("capacity"))
        );
        return "redirect:/course/list";
    }

}
