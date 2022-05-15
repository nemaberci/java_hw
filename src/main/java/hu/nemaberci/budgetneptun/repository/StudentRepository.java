package hu.nemaberci.budgetneptun.repository;

import hu.nemaberci.budgetneptun.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
