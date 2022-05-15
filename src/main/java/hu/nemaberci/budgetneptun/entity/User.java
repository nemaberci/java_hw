package hu.nemaberci.budgetneptun.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
public class User {

    @GeneratedValue
    @Id
    private Long id;

    @Column(unique = true)
    private String neptunCode;

    @Column
    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

}
