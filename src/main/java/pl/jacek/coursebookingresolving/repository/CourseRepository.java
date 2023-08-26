package pl.jacek.coursebookingresolving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jacek.coursebookingresolving.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
