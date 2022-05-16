package hu.nemaberci.budgetneptun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseChangeDTO {

    public Long courseId;
    public Integer openSpaces;

}
