package hu.nemaberci.budgetneptun.repository;

import hu.nemaberci.budgetneptun.entity.ExamEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {
    public List<ExamEntity> findAllByCourse_IdIn(List<Long> courseIds);
}
