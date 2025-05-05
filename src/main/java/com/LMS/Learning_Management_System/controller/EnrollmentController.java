package com.LMS.Learning_Management_System.controller;
import com.LMS.Learning_Management_System.dto.StudentDto;
import com.LMS.Learning_Management_System.entity.Enrollment;
import com.LMS.Learning_Management_System.service.EnrollmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollInCourse(@RequestBody Enrollment enrollment , HttpServletRequest request) {
        try {
            enrollmentService.enrollInCourse(enrollment, request);
            return ResponseEntity.ok("Student enrolled successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/view_enrolled_students/{courseId}")
    public ResponseEntity<?> viewEnrolledStudents(@PathVariable int courseId, HttpServletRequest request){
        try {
            List<StudentDto> students = enrollmentService.viewEnrolledStudents(courseId , request);
            return ResponseEntity.ok(students);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove_enrolled_student/student_id/{studentId}/course_id/{courseId}")
    public ResponseEntity<String> removeEnrolledStudent(@PathVariable int studentId, @PathVariable int courseId, HttpServletRequest request){
        try {
            enrollmentService.removeEnrolledStudent(courseId, studentId, request);
            return ResponseEntity.ok("Student removed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
