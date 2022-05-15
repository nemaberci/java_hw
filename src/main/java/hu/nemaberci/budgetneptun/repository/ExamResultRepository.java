package hu.nemaberci.budgetneptun.repository;

import hu.nemaberci.budgetneptun.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

}
