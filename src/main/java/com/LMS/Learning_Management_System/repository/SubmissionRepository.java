package com.LMS.Learning_Management_System.repository;

import com.LMS.Learning_Management_System.entity.Assignment;
import com.LMS.Learning_Management_System.entity.Student;
import com.LMS.Learning_Management_System.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findByStudentId(Student student);
    List <Submission> findAllByAssignmentId (Assignment assignmentId);
}
