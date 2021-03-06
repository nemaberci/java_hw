package hu.nemaberci.budgetneptun.repository;

import hu.nemaberci.budgetneptun.entity.ExamEntity;
import hu.nemaberci.budgetneptun.entity.ExamResultEntity;
import hu.nemaberci.budgetneptun.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResultEntity, Long> {
    List<ExamResultEntity> findAllByStudent(UserEntity student);
    List<ExamResultEntity> findAllByExam_Id(Long examId);
    Optional<ExamResultEntity> findByStudentAndTeacherAndExam(UserEntity student, UserEntity teacher, ExamEntity exam);
}
