package hu.nemaberci.budgetneptun.repository;

import hu.nemaberci.budgetneptun.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
