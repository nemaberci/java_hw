package hu.nemaberci.budgetneptun.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
public class CourseEntity {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToMany
    private List<UserEntity> teachers;

    @ManyToMany
    private List<UserEntity> students;

    @OneToMany(mappedBy = "course")
    private List<ExamEntity> exams;

}
