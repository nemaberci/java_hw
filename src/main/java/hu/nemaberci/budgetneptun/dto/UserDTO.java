package hu.nemaberci.budgetneptun.dto;

import hu.nemaberci.budgetneptun.entity.UserEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDTO {

    public Long id;
    public String neptunCode;

    public static UserDTO fromEntity(UserEntity entity) {
        return new UserDTO()
                .setId(entity.getId())
                .setNeptunCode(entity.getNeptunCode());
    }

}
