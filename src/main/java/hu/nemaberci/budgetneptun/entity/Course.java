package hu.nemaberci.budgetneptun.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Course {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToMany(mappedBy = "teaches")
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "studies")
    private List<Student> students;

    @OneToMany
    private List<Exam> exams;

}
