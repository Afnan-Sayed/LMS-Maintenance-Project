package com.LMS.Learning_Management_System.controller;

import com.LMS.Learning_Management_System.dto.CourseDto;
import com.LMS.Learning_Management_System.dto.LessonDto;
import com.LMS.Learning_Management_System.entity.Course;
import com.LMS.Learning_Management_System.entity.Lesson;
import com.LMS.Learning_Management_System.service.LessonService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class LessonControllerTest {

    @Mock
    private LessonService lessonService;

    @InjectMocks
    private LessonController lessonController;

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
    }

    @Test
    void testAddLesson(){
        Lesson lesson = new Lesson();
        Course course=new Course();
        lesson.setLessonName("Lesson 1");
        lesson.setOTP("12345");
        lesson.setCourseId(course);

        HttpServletRequest request = mock(HttpServletRequest.class);
        doNothing().when(lessonService).addLesson(any(Lesson.class), any(HttpServletRequest.class));

        ResponseEntity<String> response = lessonController.addLesson(lesson, request);
        assertEquals(200, response.getStatusCodeValue());

        assertEquals("Lesson added successfully.", response.getBody());
        verify(lessonService, times(1)).addLesson(eq(lesson), eq(request));
    }

    @Test
    void testGetAllLessons(){
        int courseId=1;
        List<LessonDto> lessons = Arrays.asList(
                new LessonDto(1, 1, "Lesson 1", "First lesson", 1, "12345","Content of the lesson",null),
                new LessonDto(2,1,"Lesson 2","Second lesson",2,"123456","Content",null)
        );
        when(lessonService.getLessonsByCourseId(eq(courseId),eq(request))).thenReturn(lessons);

        ResponseEntity<?> response = lessonController.getAllLessons(courseId,request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(lessons, response.getBody());
        verify(lessonService, times(1)).getLessonsByCourseId(eq(courseId),eq(request));
    }

    @Test
    void testGetLessonById(){
        int lessonId=1;
        LessonDto lessonDto = new LessonDto(1, 1, "Lesson 1", "First lesson", 1, "12345","Content of the lesson",null);
        when(lessonService.getLessonById(eq(lessonId), eq(request))).thenReturn(lessonDto);

        ResponseEntity<?> response = lessonController.getLessonById(lessonId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(lessonDto, response.getBody());
        verify(lessonService, times(1)).getLessonById(eq(lessonId), eq(request));
    }

    @Test
    void testUpdateLesson(){
        int lessonId=1;
        Lesson updatedLesson = new Lesson();
        updatedLesson.setLessonName("Lesson 1");
        updatedLesson.setLessonDescription("First lesson");
        updatedLesson.setOTP("12345");

        doNothing().when(lessonService).updateLesson(eq(lessonId), eq(updatedLesson), eq(request));

        ResponseEntity<String> response = lessonController.updateLesson(lessonId, updatedLesson, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Lesson updated successfully.", response.getBody());
        verify(lessonService, times(1)).updateLesson(eq(lessonId), eq(updatedLesson), eq(request));
    }

    @Test
    void testDeleteLesson(){
        int lessonId=1;
        int courseId=1;
        doNothing().when(lessonService).deleteLesson(eq(lessonId),eq(courseId), eq(request));

        ResponseEntity<String> response = lessonController.deleteLesson(lessonId,courseId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Lesson deleted successfully.", response.getBody());
        verify(lessonService, times(1)).deleteLesson(eq(lessonId),eq(courseId), eq(request));
    }

    @Test
    void testStudentEnterLesson(){
        int lessonId=1;
        int courseId=1;
        String OTP = "12345";

        HttpServletRequest request = mock(HttpServletRequest.class);
        doNothing().when(lessonService).StudentEnterLesson(eq(courseId),eq(lessonId),eq(OTP), eq(request));

        ResponseEntity<?> response = lessonController.StudentEnterLesson(courseId,lessonId,OTP, request);
        assertEquals(200, response.getStatusCodeValue());

        assertEquals("Student entered lesson successfully.", response.getBody());
        verify(lessonService, times(1)).StudentEnterLesson(eq(courseId),eq(lessonId),eq(OTP), eq(request));
    }

    @Test
    void testTrackLessonAttendances()
    {
        List <String> ans = new ArrayList<String>();

        when(lessonService.lessonAttendance(eq(1), eq(request))).thenReturn(ans);

        ResponseEntity <?> response = lessonController.trackLessonAttendances(1, request);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(ans, response.getBody());
        verify(lessonService, times(1)).lessonAttendance(eq(1), eq(request));
    }
}
