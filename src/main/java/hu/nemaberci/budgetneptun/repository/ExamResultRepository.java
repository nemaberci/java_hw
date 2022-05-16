package hu.nemaberci.budgetneptun.repository;

import hu.nemaberci.budgetneptun.entity.ExamResultEntity;
import hu.nemaberci.budgetneptun.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResultEntity, Long> {
    List<ExamResultEntity> findAllByStudent(UserEntity student);
}
