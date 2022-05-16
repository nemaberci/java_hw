package hu.nemaberci.budgetneptun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
public class ExamResultEntity {

    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String comment;

    @Column
    private Integer points;

    @ManyToOne
    private ExamEntity exam;

    @ManyToOne
    private UserEntity student;

    @ManyToOne
    private UserEntity teacher;

}
