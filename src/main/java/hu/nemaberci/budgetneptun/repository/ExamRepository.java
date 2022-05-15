package hu.nemaberci.budgetneptun.repository;

import hu.nemaberci.budgetneptun.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

}
