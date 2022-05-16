package hu.nemaberci.budgetneptun.controller;

import hu.nemaberci.budgetneptun.dto.CourseChangeDTO;
import hu.nemaberci.budgetneptun.entity.CourseEntity;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class CourseWebsocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public CourseWebsocketController(
            SimpMessagingTemplate simpMessagingTemplate
    ) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/courses")
    public void courseChanged(CourseEntity courseEntity) {
        simpMessagingTemplate.convertAndSend(
                "/topic/courses",
                new CourseChangeDTO(
                        courseEntity.getId(),
                        courseEntity.getCapacity() - courseEntity.getStudents().size()
                )
        );
    }

    @Component
    public class CourseChangedCallback implements Consumer<CourseEntity> {

        @Override
        public void accept(CourseEntity courseEntity) {
            courseChanged(courseEntity);
        }
    }

}
