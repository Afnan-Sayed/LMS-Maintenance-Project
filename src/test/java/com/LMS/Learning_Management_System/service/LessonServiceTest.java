package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.dto.CourseDto;
import com.LMS.Learning_Management_System.dto.LessonDto;
import com.LMS.Learning_Management_System.entity.*;
import com.LMS.Learning_Management_System.repository.*;
import com.LMS.Learning_Management_System.repository.EnrollmentRepository;
import com.LMS.Learning_Management_System.repository.LessonAttendanceRepository;
import com.LMS.Learning_Management_System.repository.LessonRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class LessonServiceTest {
    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private LessonService lessonService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private LessonAttendanceRepository lessonAttendanceRepository;

    private Users instructorUser;
    private Users studentUser;
    private Lesson lesson;
    private Course course;
    private UsersType instructorType;
    private UsersType studentType;
    private List<Lesson> lessons;
    private List<Enrollment> enrollments;
    private LessonAttendance lessonAttendance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        instructorType = new UsersType();
        instructorType.setUserTypeId(3);

        studentType = new UsersType();
        studentType.setUserTypeId(2);

        instructorUser = new Users();
        instructorUser.setUserId(1);
        instructorUser.setUserTypeId(instructorType);

        studentUser = new Users();
        studentUser.setUserId(2);
        studentUser.setUserTypeId(studentType);

        course = new Course();
        course.setCourseId(1);
        course.setCourseName("Java Basics");
        course.setInstructorId(new Instructor());
        course.getInstructorId().setUserAccountId(1);

        lesson = new Lesson();
        lesson.setLessonId(1);
        lesson.setLessonName("Lesson 1");
        lesson.setCourseId(course);
        lesson.setLessonDescription("The first lesson");
        lesson.setOTP("12345");
        lesson.setLessonOrder(1);
        lessons = new ArrayList<>();
        lessons.add(lesson);

        Student student = new Student();
        student.setUserAccountId(2);
        student.setUserId(studentUser);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollments = new ArrayList<>();
        enrollments.add(enrollment);

        lessonAttendance = new LessonAttendance();
        lessonAttendance.setLessonId(lesson);
        lessonAttendance.setStudentId(student);
    }

    @Test
    void testAddLesson_UserNotLoggedIn() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.addLesson(lesson, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testAddLesson_Success() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        lessonService.addLesson(lesson, request);
        verify(lessonRepository, times(1)).save(lesson);
    }

    @Test
    void testGetLessonById_Success() {

        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(instructorUser);

        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));

        LessonDto lessonDto = lessonService.getLessonById(1, request);
        assertNotNull(lessonDto);
        assertEquals("Lesson 1", lessonDto.getLessonName());
        verify(lessonRepository, times(1)).findById(1);
    }


    @Test
    void testGetLessonsByCourseId_Success(){
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        when(lessonRepository.findByCourseId(course)).thenReturn(lessons);

        List<LessonDto> lessonDtos = lessonService.getLessonsByCourseId(course.getCourseId(), request);

        assertNotNull(lessonDtos);
        assertEquals("Lesson 1", lessonDtos.get(0).getLessonName());
        assertEquals(1, lessonDtos.size());
        verify(lessonRepository, times(1)).findByCourseId(course);
    }

    @Test
    void testDeleteLesson_Success(){
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(instructorUser);

        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        lessonService.deleteLesson(1,1, request);

        verify(lessonRepository, times(1)).deleteById(1);
    }

    @Test
    void testStudentEnterLesson_Success(){
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(studentUser);

        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourse(course)).thenReturn(enrollments);
        when(lessonAttendanceRepository.existsByLessonIdAndStudentId(lesson, new Student())).thenReturn(false);

        lessonService.StudentEnterLesson(1, 1, "12345", request);
        verify(lessonAttendanceRepository, times(1)).save(any(LessonAttendance.class));
    }

    @Test
    void testLessonAttendance_noLoggedInUser()
    {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(null);
        when(lessonRepository.existsById(1)).thenReturn(true);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(lessonAttendanceRepository.findAllByLessonId(lesson)).thenReturn(List.of(lessonAttendance));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
        {
            lessonService.lessonAttendance(1, request);
        });

        assertEquals("No logged in user is found.", exception.getMessage());
    }

    @Test
    void testLessonAttendance_notInstructor()
    {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(lessonRepository.existsById(1)).thenReturn(true);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(lessonAttendanceRepository.findAllByLessonId(lesson)).thenReturn(List.of(lessonAttendance));


        Exception exception = assertThrows(IllegalArgumentException.class, () ->
        {
            lessonService.lessonAttendance(1, request);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }

    @Test
    void testLessonAttendance_notLessonInstructor()
    {
        Users inValidInstructorUser = new Users();
        inValidInstructorUser.setUserId(3);
        inValidInstructorUser.setUserTypeId(instructorType);

        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(inValidInstructorUser);
        when(lessonRepository.existsById(1)).thenReturn(true);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(lessonAttendanceRepository.findAllByLessonId(lesson)).thenReturn(List.of(lessonAttendance));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
        {
            lessonService.lessonAttendance(1, request);
        });

        assertEquals("Logged-in instructor does not have access for this lesson attendances.", exception.getMessage());
    }

    @Test
    void testLessonAttendance_lessonNotFound()
    {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(instructorUser);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
        {
            lessonService.lessonAttendance(2, request);
        });

        assertEquals("Lesson with ID 2 not found.", exception.getMessage());
    }

    @Test
    void testLessonAttendance()
    {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(lessonRepository.existsById(1)).thenReturn(true);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(lessonAttendanceRepository.findAllByLessonId(lesson)).thenReturn(List.of(lessonAttendance));

        List <String> quizGrades = lessonService.lessonAttendance(1, request);

        assertEquals(1, quizGrades.size());
    }
}
