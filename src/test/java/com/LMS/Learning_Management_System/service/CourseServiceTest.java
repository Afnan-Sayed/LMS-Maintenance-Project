package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.dto.CourseDto;
import com.LMS.Learning_Management_System.entity.Course;
import com.LMS.Learning_Management_System.entity.Instructor;
import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.entity.UsersType;
import com.LMS.Learning_Management_System.repository.CourseRepository;
import com.LMS.Learning_Management_System.repository.EnrollmentRepository;
import com.LMS.Learning_Management_System.repository.InstructorRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CourseService courseService;

    private Users instructorUser;
    private Course course;
    private UsersType instructorType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        instructorType = new UsersType();
        instructorType.setUserTypeId(3);

        instructorUser = new Users();
        instructorUser.setUserId(1);
        instructorUser.setUserTypeId(instructorType);

        course = new Course();
        course.setCourseId(1);
        course.setCourseName("Java Basics");
        course.setInstructorId(new Instructor());
        course.getInstructorId().setUserAccountId(1);
    }

    @Test
    void testAddCourse_DuplicateCourseName() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findByCourseName(course.getCourseName())).thenReturn(course);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.addCourse(course, request, 1);
        });

        assertEquals("This CourseName already exist", exception.getMessage());
    }

    @Test
    void testAddCourse_UserNotLoggedIn() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.addCourse(course, request, 1);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testGetCourseById_Success() {

        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        CourseDto courseDto = courseService.getCourseById(1, request);
        assertNotNull(courseDto);
        assertEquals("Java Basics", courseDto.getCourseName());

        verify(courseRepository, times(1)).findById(1);
    }


    @Test
    void testDeleteCourse_Success() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        courseService.deleteCourse(1, request);

        verify(courseRepository, times(1)).delete(course);
    }


    @Test
    void testUploadMediaFile_Success() throws Exception {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("example.mp4");

        courseService.uploadMediaFile(1, mockFile, request);

        verify(mockFile, times(1)).transferTo(any(File.class));
        verify(courseRepository, times(1)).save(course);
    }

}
