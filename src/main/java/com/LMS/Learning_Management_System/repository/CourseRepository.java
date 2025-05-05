package com.LMS.Learning_Management_System.repository;

import com.LMS.Learning_Management_System.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


//@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findByCourseName(String courseName);
    @Query("SELECT CASE WHEN COUNT(course) > 0 THEN true ELSE false END " +
            "FROM Course course " +
            "WHERE course.instructorId.userAccountId = :instructorId " +
            "AND course.courseId = :courseId")
    boolean findByInstructorId(int instructorId , int courseId);
}
