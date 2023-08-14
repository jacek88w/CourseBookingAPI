package pl.jacek.coursebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jacek.coursebooking.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
