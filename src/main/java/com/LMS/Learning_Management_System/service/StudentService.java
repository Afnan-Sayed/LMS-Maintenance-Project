package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.Student;
import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Student> findById(int userId) {
        return studentRepository.findById(userId);
    }

    public void save(int studentId ,Student student , HttpServletRequest request) {
        Users loggedInInstructor = (Users) request.getSession().getAttribute("user");
        if (loggedInInstructor == null) {
            throw new IllegalArgumentException("User is not logged in.");
        }
        if(studentId != loggedInInstructor.getUserId()) {
            throw new IllegalArgumentException("You are not authorized to perform this action.");
        }
        Student newStudent = studentRepository.getReferenceById(studentId);
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        studentRepository.save(newStudent);
    }
}
