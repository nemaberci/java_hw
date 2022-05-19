package hu.nemaberci.budgetneptun.controller;

import hu.nemaberci.budgetneptun.dto.CourseDTO;
import hu.nemaberci.budgetneptun.dto.ExamDTO;
import hu.nemaberci.budgetneptun.service.CourseService;
import hu.nemaberci.budgetneptun.service.ExamService;
import hu.nemaberci.budgetneptun.service.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/exam")
public class ExamController {

    private final CourseService courseService;
    private final UserService userService;
    private final ExamService examService;

    public ExamController(CourseService courseService,
            UserService userService,
            ExamService examService
    ) {
        this.courseService = courseService;
        this.userService = userService;
        this.examService = examService;
    }

    @GetMapping("/mine")
    public String myExams(Model model) {
        final var studentCourseIds = courseService.getAll()
                .stream().filter(
                        course -> course.getStudentIds().contains(userService.currentUserId())
                ).map(CourseDTO::getId).collect(Collectors.toList());
        final var teacherCourseIds = courseService.getAll()
                .stream().filter(
                        course -> course.getTeacherIds().contains(userService.currentUserId())
                ).map(CourseDTO::getId).collect(Collectors.toList());
        model.addAttribute("userId", userService.currentUserId());
        Set<ExamDTO> exams = new HashSet<>();
        exams.addAll(
                examService.getAllForCoursesWithStudent(
                        studentCourseIds,
                        userService.currentUserId()
                )
        );
        exams.addAll(
                examService.getAllForCourses(teacherCourseIds)
        );
        model.addAttribute("exams", new ArrayList<>(exams));
        model.addAttribute("courses", courseService.getAll());
        return "exam/exams";
    }

    @GetMapping("/apply")
    public String notMyExams(Model model) {
        final var studentCourseIds = courseService.getAll()
                .stream().filter(
                        course -> course.getStudentIds().contains(userService.currentUserId())
                ).map(CourseDTO::getId).collect(Collectors.toList());
        model.addAttribute("userId", userService.currentUserId());
        // Teachers don't apply to exams
        model.addAttribute(
                "exams", examService.getAllForCoursesWithoutStudent(
                        studentCourseIds,
                        userService.currentUserId()
                ));
        model.addAttribute("courses", courseService.getAll());
        return "exam/exams";
    }

    @GetMapping("/update/{id}")
    public String updateExam(Model model, @PathVariable Long id) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("exam", examService.getById(id));
        return "exam/exam";
    }

    @GetMapping("/new")
    public String newExam(Model model) {
        model.addAttribute("courses", courseService.getAll());
        return "exam/exam";
    }

    @PostMapping("/new")
    public String newExam(@RequestParam Map<String, String> body) {
        examService.create(
                body.get("description"),
                LocalDateTime.parse(body.get("time")),
                Long.parseLong(body.get("courseId")),
                Integer.parseInt(body.get("capacity"))
        );
        return "redirect:/exam/mine";
    }

    @GetMapping("/addstudent/{id}")
    public String addStudent(@PathVariable Long id, Model model) {
        var exam = examService.getById(id);
        var students = userService.getStudents().stream()
                .filter(user -> exam.getAttendantIds().stream()
                        .noneMatch(studentId -> user.getId().equals(studentId)))
                .collect(Collectors.toList());
        model.addAttribute("users", students);
        model.addAttribute("exam", exam);
        model.addAttribute("remove", false);
        return "core/add_user";
    }

    @GetMapping("/removestudent/{id}")
    public String removeStudent(@PathVariable Long id, Model model) {
        var exam = examService.getById(id);
        var students = userService.getStudents().stream()
                .filter(user -> exam.getAttendantIds().stream()
                        .anyMatch(studentId -> user.getId().equals(studentId)))
                .collect(Collectors.toList());
        model.addAttribute("users", students);
        model.addAttribute("exam", exam);
        model.addAttribute("remove", true);
        return "core/add_user";
    }

    @PostMapping("/addstudent/{id}")
    public String addStudentPost(@PathVariable Long id, @RequestParam Long student) {
        examService.applyToExam(
                id,
                student
        );
        return "redirect:/exam/mine";
    }

    @PostMapping("/removestudent/{id}")
    public String removeStudentPost(@PathVariable Long id, @RequestParam Long student) {
        examService.removeFromExam(
                id,
                student
        );
        return "redirect:/exam/mine";
    }

    @PostMapping("/update/{id}")
    public String updateOnPost(@RequestParam Map<String, String> body, @PathVariable Long id) {
        examService.update(
                id,
                body.get("description"),
                LocalDateTime.parse(body.get("time")),
                Long.parseLong(body.get("courseId")),
                Integer.parseInt(body.get("capacity"))
        );
        return "redirect:/exam/mine";
    }

    @GetMapping("/review/{examId}")
    public String reviewExam(Model model, @PathVariable Long examId) {
        var exam = examService.getById(examId);
        model.addAttribute(
                "students", exam.getAttendantIds().stream().map(userService::getStudent)
                        .collect(Collectors.toList()));
        return "/exam/review";
    }

    @PostMapping("/review/{examId}")
    public String reviewExamPost(@RequestParam Map<String, String> body, @PathVariable Long examId
    ) {
        examService.evaluateExam(
                examId,
                userService.currentUserId(),
                Long.parseLong(body.get("studentId")),
                body.get("comment"),
                Integer.parseInt(body.get("points"))
        );
        return "redirect:/exam/mine";
    }

    @GetMapping("/results")
    public String myResults(Model model) {
        model.addAttribute("results", examService.resultsOfStudent(userService.currentUserId()));
        return "/exam/my_results";
    }

}
