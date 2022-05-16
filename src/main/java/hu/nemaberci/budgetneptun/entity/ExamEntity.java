package hu.nemaberci.budgetneptun.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
public class ExamEntity {

    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String description;

    @Column
    private LocalDateTime time;

    @Column
    private Integer capacity;

    @ManyToMany
    private List<UserEntity> attendants;

}
