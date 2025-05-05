package com.LMS.Learning_Management_System.controller;

import com.LMS.Learning_Management_System.dto.StudentDto;
import com.LMS.Learning_Management_System.entity.Course;
import com.LMS.Learning_Management_System.entity.Enrollment;
import com.LMS.Learning_Management_System.entity.Student;
import com.LMS.Learning_Management_System.service.EnrollmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class EnrollmentControllerTest {


    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
    }

    @Test
    void testEnrollInCourse(){
        Enrollment enrollment= new Enrollment();
        Student student = new Student();
        Course course = new Course();
        enrollment.setCourse(course);
        enrollment.setStudent(student);

        HttpServletRequest request = mock(HttpServletRequest.class);
        doNothing().when(enrollmentService).enrollInCourse(any(Enrollment.class), any(HttpServletRequest.class));

        ResponseEntity<String> response = enrollmentController.enrollInCourse(enrollment, request);
        assertEquals(200, response.getStatusCodeValue());

        assertEquals("Student enrolled successfully!", response.getBody());
        verify(enrollmentService, times(1)).enrollInCourse(eq(enrollment), eq(request));
    }

    @Test
    void testViewEnrolledStudents(){
        int courseId=1;
        List<StudentDto> students = Arrays.asList(
                new StudentDto(1,"Mohra","Deyaa")
        );
        when(enrollmentService.viewEnrolledStudents(eq(courseId), eq(request))).thenReturn(students);

        ResponseEntity<?> response = enrollmentController.viewEnrolledStudents(courseId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(students, response.getBody());
        verify(enrollmentService, times(1)).viewEnrolledStudents(courseId, request);
    }

    @Test
    void testRemoveEnrolledStudent(){
        int courseId=1;
        int studentId=1;
        doNothing().when(enrollmentService).removeEnrolledStudent(eq(studentId), eq(courseId), eq(request));

        ResponseEntity<String> response = enrollmentController.removeEnrolledStudent(studentId, courseId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Student removed successfully.", response.getBody());
        verify(enrollmentService, times(1)).removeEnrolledStudent(eq(studentId),eq(courseId), eq(request));
    }

}