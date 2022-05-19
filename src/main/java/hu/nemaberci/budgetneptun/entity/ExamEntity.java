package hu.nemaberci.budgetneptun.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
public class ExamEntity {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToMany
    private List<UserEntity> attendants;

    @ManyToOne(optional = false)
    private CourseEntity course;

}
