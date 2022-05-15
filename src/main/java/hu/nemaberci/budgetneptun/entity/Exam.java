package hu.nemaberci.budgetneptun.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Exam {

    @GeneratedValue
    @Id
    private Long id;

    @OneToOne(optional = true)
    private ExamResult result;

    @Column
    private LocalDateTime time;

    @Column
    private Integer maxAttendants;

    @ManyToMany
    private List<Student> attendants;

}
