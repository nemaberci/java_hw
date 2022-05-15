package hu.nemaberci.budgetneptun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ExamResult {

    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String comment;

    @Column
    private Integer points;

    @OneToOne(mappedBy = "result")
    private Exam exam;

}
