package hu.nemaberci.budgetneptun.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Teacher extends User {

    @ManyToMany
    private List<Course> teaches;

}
