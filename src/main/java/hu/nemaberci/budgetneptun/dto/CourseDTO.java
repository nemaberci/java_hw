package hu.nemaberci.budgetneptun.dto;

import hu.nemaberci.budgetneptun.entity.CourseEntity;
import hu.nemaberci.budgetneptun.entity.ExamEntity;
import hu.nemaberci.budgetneptun.entity.UserEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CourseDTO {

    public Long id;
    public String name;
    public String description;
    public Integer capacity;
    public List<Long> teacherIds;
    public List<Long> studentIds;
    public List<Long> examIds;

    public static CourseDTO fromEntity(CourseEntity entity) {
        return new CourseDTO()
                .setId(entity.getId())
                .setDescription(entity.getDescription())
                .setName(entity.getName())
                .setCapacity(entity.getCapacity())
                .setExamIds(entity.getExams().stream()
                        .map(ExamEntity::getId).collect(Collectors.toList()))
                .setStudentIds(entity.getStudents().stream()
                        .map(UserEntity::getId).collect(Collectors.toList()))
                .setTeacherIds(entity.getTeachers().stream()
                        .map(UserEntity::getId).collect(Collectors.toList()));
    }

}
