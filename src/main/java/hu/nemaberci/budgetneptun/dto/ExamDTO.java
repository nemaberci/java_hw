package hu.nemaberci.budgetneptun.dto;

import hu.nemaberci.budgetneptun.entity.ExamEntity;
import hu.nemaberci.budgetneptun.entity.UserEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExamDTO {

    public Long id;
    public String description;
    public LocalDateTime time;
    public String timeString;
    public List<Long> attendantIds;
    public Integer capacity;
    public String courseName;
    public List<Long> teacherIds;

    public static ExamDTO fromEntity(ExamEntity entity) {
        return new ExamDTO()
                .setAttendantIds(entity.getAttendants().stream()
                        .map(UserEntity::getId).collect(Collectors.toList()))
                .setDescription(entity.getDescription())
                .setTime(entity.getTime())
                .setTimeString(entity.getTime().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss")
                ))
                .setId(entity.getId())
                .setCourseName(entity.getCourse().getName())
                .setTeacherIds(
                        entity.getCourse().getTeachers().stream().map(UserEntity::getId)
                                .collect(Collectors.toList()))
                .setCapacity(entity.getCapacity());
    }

}
