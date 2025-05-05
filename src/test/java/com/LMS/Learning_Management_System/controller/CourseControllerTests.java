package com.LMS.Learning_Management_System.controller;

import com.LMS.Learning_Management_System.dto.CourseDto;
import com.LMS.Learning_Management_System.entity.Course;
import com.LMS.Learning_Management_System.entity.Instructor;
import com.LMS.Learning_Management_System.service.CourseService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    @Mock
    private CourseService courseService;


    @InjectMocks
    private CourseController courseController;

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
    }

    @Test
    void testAddCourse() {
        // Arrange
        Course course = new Course();
        Instructor instructor = new Instructor();

        course.setCourseName("Java Basics");
        course.setDescription("Learn Java fundamentals");
        course.setInstructorId(instructor);


        HttpServletRequest request = mock(HttpServletRequest.class);
        doNothing().when(courseService).addCourse(any(Course.class), any(HttpServletRequest.class), anyInt());


        ResponseEntity<String> response = courseController.addCourse(course, request);
        assertEquals(200, response.getStatusCodeValue());

        assertEquals("Course created successfully.", response.getBody());
        verify(courseService, times(1)).addCourse(eq(course), eq(request), anyInt());
    }

    @Test
    void testGetCourseById() {
        int courseId = 1;
        CourseDto courseDto = new CourseDto(1, "Java Basics", "Learn Java", 10, null, "John Doe");

        when(courseService.getCourseById(eq(courseId), eq(request))).thenReturn(courseDto);

        ResponseEntity<?> response = courseController.getCourseById(courseId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseDto, response.getBody());
        verify(courseService, times(1)).getCourseById(eq(courseId), eq(request));
    }

    @Test
    void testGetAllCourses() {
        List<CourseDto> courseList = Arrays.asList(
                new CourseDto(1, "Java Basics", "Learn Java", 10, null, "John Doe"),
                new CourseDto(2, "Spring Boot", "Learn Spring Boot", 15, null, "Jane Smith")
        );

        when(courseService.getAllCourses(eq(request))).thenReturn(courseList);

        ResponseEntity<?> response = courseController.getAllCourses(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseList, response.getBody());
        verify(courseService, times(1)).getAllCourses(eq(request));
    }

    @Test
    void testUpdateCourse() {
        int courseId = 1;
        Course updatedCourse = new Course();
        updatedCourse.setCourseName("Advanced Java");
        updatedCourse.setDescription("Learn advanced Java concepts");

        doNothing().when(courseService).updateCourse(eq(courseId), eq(updatedCourse), eq(request));

        ResponseEntity<String> response = courseController.updateCourse(courseId, updatedCourse, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Course updated successfully.", response.getBody());
        verify(courseService, times(1)).updateCourse(eq(courseId), eq(updatedCourse), eq(request));
    }

    @Test
    void testDeleteCourse() {
        int courseId = 1;

        doNothing().when(courseService).deleteCourse(eq(courseId), eq(request));

        ResponseEntity<String> response = courseController.deleteCourse(courseId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Course deleted successfully.", response.getBody());
        verify(courseService, times(1)).deleteCourse(eq(courseId), eq(request));
    }
}
