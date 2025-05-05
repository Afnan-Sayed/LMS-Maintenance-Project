package com.LMS.Learning_Management_System.repository;

import com.LMS.Learning_Management_System.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Query("SELECT q FROM Quiz q WHERE q.course.courseId = :courseId ")
    List<Quiz> getQuizzesByCourseId(@Param("courseId") int courseId );

}
