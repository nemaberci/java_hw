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

@Entity(name = "users")
@Data
@Accessors(chain = true)
public class UserEntity {

    @GeneratedValue
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String neptunCode;

    @Column(nullable = false)
    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

}
