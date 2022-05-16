package hu.nemaberci.budgetneptun.repository;

import hu.nemaberci.budgetneptun.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByNeptunCode(String neptunCode);
}
