package com.LMS.Learning_Management_System.repository;

import com.LMS.Learning_Management_System.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
}
