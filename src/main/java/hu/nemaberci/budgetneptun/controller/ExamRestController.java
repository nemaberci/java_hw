package hu.nemaberci.budgetneptun.controller;

import hu.nemaberci.budgetneptun.service.ExamService;
import hu.nemaberci.budgetneptun.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exam")
public class ExamRestController {

    private final UserService userService;
    private final ExamService examService;

    public ExamRestController(UserService userService,
            ExamService examService
    ) {
        this.userService = userService;
        this.examService = examService;
    }

    @PostMapping("/apply")
    public ResponseEntity<Boolean> apply(@RequestParam Long id) {
        examService.applyToExam(
                id,
                userService.currentUserId()
        );
        return ResponseEntity.ok(true);
    }

    @PostMapping("/leave")
    public ResponseEntity<Boolean> leave(@RequestParam Long id) {
        examService.removeFromExam(
                id,
                userService.currentUserId()
        );
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        examService.delete(id);
        return ResponseEntity.ok(true);
    }

}
