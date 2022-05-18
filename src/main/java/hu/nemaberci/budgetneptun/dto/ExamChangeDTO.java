package hu.nemaberci.budgetneptun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExamChangeDTO {

    public Long examId;
    public Integer openSpaces;

}
