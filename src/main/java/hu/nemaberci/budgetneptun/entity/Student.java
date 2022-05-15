package hu.nemaberci.budgetneptun.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Student extends User {

    @ManyToMany
    private List<Course> studies;

    @ManyToMany
    private List<Exam> exams;

}
