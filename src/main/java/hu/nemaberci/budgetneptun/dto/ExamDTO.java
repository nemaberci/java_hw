package hu.nemaberci.budgetneptun.dto;

import hu.nemaberci.budgetneptun.entity.ExamEntity;
import hu.nemaberci.budgetneptun.entity.UserEntity;
import java.time.LocalDateTime;
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
    public List<Long> attendantIds;
    public Integer capacity;

    public static ExamDTO fromEntity(ExamEntity entity) {
        return new ExamDTO()
                .setAttendantIds(entity.getAttendants().stream()
                        .map(UserEntity::getId).collect(Collectors.toList()))
                .setDescription(entity.getDescription())
                .setTime(entity.getTime())
                .setId(entity.getId())
                .setCapacity(entity.getCapacity());
    }

}
