package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.Course;
import com.LMS.Learning_Management_System.entity.Enrollment;
import com.LMS.Learning_Management_System.entity.Student;
import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.repository.CourseRepository;
import com.LMS.Learning_Management_System.repository.EnrollmentRepository;
import com.LMS.Learning_Management_System.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Users loggedInUser;
    private Student student;
    private Course course;
    private Enrollment enrollmentRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        loggedInUser = new Users();
        loggedInUser.setUserId(1);

        student = new Student();
        student.setUserAccountId(1);
        student.setFirstName("John");
        student.setLastName("Doe");


        course = new Course();
        course.setCourseId(1);
        course.setCourseName("Java Basics");


        enrollmentRequest = new Enrollment();
        enrollmentRequest.setStudent(student);
        enrollmentRequest.setCourse(course);
    }

    @Test
    void testEnrollInCourse_Success() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(loggedInUser);

        when(studentRepository.findById(student.getUserAccountId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(false);

        enrollmentService.enrollInCourse(enrollmentRequest, request);

        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void testEnrollInCourse_NoUserLoggedIn() {

        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(null);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.enrollInCourse(enrollmentRequest, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testEnrollInCourse_StudentIdMismatch() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(loggedInUser);

        Enrollment enrollmentRequestWithMismatch = new Enrollment();
        enrollmentRequestWithMismatch.setStudent(new Student());
        enrollmentRequestWithMismatch.getStudent().setUserAccountId(2);
        enrollmentRequestWithMismatch.setCourse(course);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.enrollInCourse(enrollmentRequestWithMismatch, request);
        });

        assertEquals("Student ID mismatch. Please provide the correct ID.", exception.getMessage());
    }

    @Test
    void testEnrollInCourse_StudentAlreadyEnrolled() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(loggedInUser);

        when(studentRepository.findById(student.getUserAccountId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(true); // Already enrolled

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.enrollInCourse(enrollmentRequest, request);
        });

        assertEquals("Student is already enrolled in this course.", exception.getMessage());
    }

    @Test
    void testEnrollInCourse_StudentNotFound() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(loggedInUser);

        when(studentRepository.findById(student.getUserAccountId())).thenReturn(Optional.empty());
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.enrollInCourse(enrollmentRequest, request);
        });

        assertEquals("No student found with the given ID.", exception.getMessage());
    }

    @Test
    void testEnrollInCourse_CourseNotFound() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(loggedInUser);

        when(studentRepository.findById(student.getUserAccountId())).thenReturn(Optional.of(student));

        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.enrollInCourse(enrollmentRequest, request);
        });

        assertEquals("No course found with the given ID: 1", exception.getMessage());
    }
}
