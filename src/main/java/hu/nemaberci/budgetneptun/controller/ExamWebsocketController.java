package hu.nemaberci.budgetneptun.controller;

import hu.nemaberci.budgetneptun.dto.CourseChangeDTO;
import hu.nemaberci.budgetneptun.dto.ExamChangeDTO;
import hu.nemaberci.budgetneptun.entity.CourseEntity;
import hu.nemaberci.budgetneptun.entity.ExamEntity;
import java.util.function.Consumer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class ExamWebsocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ExamWebsocketController(
            SimpMessagingTemplate simpMessagingTemplate
    ) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/exams")
    public void courseChanged(ExamEntity exam) {
        simpMessagingTemplate.convertAndSend(
                "/topic/exams",
                new ExamChangeDTO(
                        exam.getId(),
                        exam.getCapacity() - exam.getAttendants().size()
                )
        );
    }

    @Component
    public class CourseChangedCallback implements Consumer<ExamEntity> {

        @Override
        public void accept(ExamEntity exam) {
            courseChanged(exam);
        }
    }

}
