package pl.jacek.coursebooking.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pl.jacek.coursebooking.dto.CourseDTO;
import pl.jacek.coursebooking.entity.Course;
import pl.jacek.coursebooking.exceptions.NoSuchEntityException;
import pl.jacek.coursebooking.repository.CourseRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if(courseOptional.isPresent()) {
            return courseOptional.get();
        } else {
            throw new NoSuchEntityException("Course not found");
        } //todo duplicated
    }

    public String create(CourseDTO courseDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        courseRepository.save(course);
        return "Course created with id: " + course.getId();
    }

    public String deleteById(Long id) {
        courseRepository.deleteById(id);
        return "Course with ID " + id + " deleted";
    }

    public String deleteAll() {
        courseRepository.deleteAll();
        return "All courses deleted";
    }

    public String update(CourseDTO courseDTO, Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        Course courseDB;
        if(courseOptional.isPresent()) {
            courseDB = courseOptional.get();
        } else {
            throw new NoSuchEntityException("Course not found");
        }

        if(Objects.nonNull(courseDTO.getName()) &&
                !"".equalsIgnoreCase(courseDTO.getName())) { // <-- name
            courseDB.setName(courseDTO.getName());
        }

        if(Objects.nonNull(courseDTO.getAdvancement()) &&
                !"".equalsIgnoreCase(String.valueOf(courseDTO.getAdvancement()))) { // <-- advancement
            courseDB.setAdvancement(courseDTO.getAdvancement());
        }

        if(Objects.nonNull(courseDTO.getTeacher())) { // <-- Teacher
            courseDB.setTeacher(courseDTO.getTeacher());
        }

        courseRepository.save(courseDB);
        return "Course with ID: " + id + " updated";
    }
}
