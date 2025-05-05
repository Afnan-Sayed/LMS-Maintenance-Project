package com.LMS.Learning_Management_System.repository;

import com.LMS.Learning_Management_System.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonAttendanceRepository extends JpaRepository<LessonAttendance, Integer> {
    boolean existsByLessonIdAndStudentId(Lesson lessonId , Student studentId);
    List <LessonAttendance> findAllByLessonId (Lesson LessonId);
}
