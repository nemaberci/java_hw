package hu.nemaberci.budgetneptun.repository;

import hu.nemaberci.budgetneptun.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
