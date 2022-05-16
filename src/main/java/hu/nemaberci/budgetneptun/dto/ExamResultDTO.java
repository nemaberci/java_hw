package hu.nemaberci.budgetneptun.dto;

import hu.nemaberci.budgetneptun.entity.ExamResultEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExamResultDTO {

    public Long id;
    public String comment;
    public Integer points;
    public Long examId;
    public Long studentId;
    public Long teacherId;

    public static ExamResultDTO fromEntity(ExamResultEntity entity) {
        return new ExamResultDTO()
                .setExamId(entity.getExam().getId())
                .setComment(entity.getComment())
                .setId(entity.getId())
                .setPoints(entity.getPoints())
                .setStudentId(entity.getStudent().getId())
                .setTeacherId(entity.getTeacher().getId());
    }

}
