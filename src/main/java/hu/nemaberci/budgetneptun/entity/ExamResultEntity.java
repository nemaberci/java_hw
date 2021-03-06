package hu.nemaberci.budgetneptun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "teacher_id", "exam_id"})
        }
)
public class ExamResultEntity {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Integer points;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exam_id")
    private ExamEntity exam;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private UserEntity student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher;

}
