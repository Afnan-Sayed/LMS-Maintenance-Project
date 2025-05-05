package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.dto.CourseDto;
import com.LMS.Learning_Management_System.dto.StudentDto;
import com.LMS.Learning_Management_System.entity.*;
import com.LMS.Learning_Management_System.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentService enrollmentService;
    private final NotificationsService notificationsService;

    public CourseService(InstructorRepository instructorRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, EnrollmentService enrollmentService, NotificationsService notificationsService) {
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentService = enrollmentService;
        this.notificationsService = notificationsService;
    }
    public void addCourse(Course course , HttpServletRequest request , int instructorId){
        // auth
        Users loggedInInstructor = (Users) request.getSession().getAttribute("user");
        if (loggedInInstructor == null) {
            throw new IllegalArgumentException("No user is logged in.");
        }
        if (loggedInInstructor.getUserTypeId() == null || loggedInInstructor.getUserTypeId().getUserTypeId() != 3) {
            throw new IllegalArgumentException("Logged-in user is not an instructor.");
        }
        if(instructorId != loggedInInstructor.getUserId()){
            throw new IllegalArgumentException("Logged-in user is an another instructor.");
        }
        //

        if (courseRepository.findByCourseName(course.getCourseName()) != null) {
            throw new IllegalArgumentException("This CourseName already exist");
        }
        course.setCreationDate(new Date(System.currentTimeMillis()));
        if (course.getInstructorId() == null|| course.getInstructorId().getUserAccountId()==0) {
            throw new IllegalArgumentException("InstructorId cannot be null");
        }
        Instructor instructor = instructorRepository.findById(course.getInstructorId().getUserAccountId())
                .orElseThrow(() -> new IllegalArgumentException("No such Instructor"));
        course.setInstructorId(instructor);
        courseRepository.save(course);
    }
    public List<CourseDto> getAllCourses(HttpServletRequest request) {
        Users loggedInInstructor = (Users) request.getSession().getAttribute("user");
        if (loggedInInstructor == null) {
            throw new IllegalArgumentException("No user is logged in.");
        }

        List<Course> courses = courseRepository.findAll();

        return convertToCourseDtoList(courses);

    }

    public CourseDto getCourseById(int id ,HttpServletRequest request) {
        Users loggedInInstructor = (Users) request.getSession().getAttribute("user");
        if (loggedInInstructor == null) {
            throw new IllegalArgumentException("No user is logged in.");
        }
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No course found with the given ID: " + id));
        if(loggedInInstructor.getUserTypeId().getUserTypeId() == 2){
            int flag = 0;
            List<Enrollment>enrollments = enrollmentRepository.findByCourse(course);
            for (Enrollment enrollment : enrollments) {
                if(enrollment.getStudent().getUserAccountId() == loggedInInstructor.getUserId()){
                    flag = 1;
                }
            }
            if(flag==0)
            {
                throw new IllegalArgumentException("You are not enrolled to this course.");
            }
        }
        return new CourseDto(
                course.getCourseId(),
                course.getCourseName(),
                course.getDescription(),
                course.getDuration(),
                course.getMedia(),
                course.getInstructorId().getFirstName()
        );

    }
    public void updateCourse(int courseId, Course updatedCourse, HttpServletRequest request) {

        Course existingCourse = check_before_logic(courseId , request);
        existingCourse.setCourseName(updatedCourse.getCourseName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setDuration(updatedCourse.getDuration());

        courseRepository.save(existingCourse);
    }
    public void deleteCourse(int courseId, HttpServletRequest request) {
        Course existingCourse = check_before_logic(courseId , request);
        courseRepository.delete(existingCourse);
    }
    public void uploadMediaFile(int courseId, MultipartFile file, HttpServletRequest request) {
        Course course = check_before_logic(courseId , request);

        String uploadDir = "media/uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File destination = new File(uploadDir + fileName);

        try {
            file.transferTo(destination);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed.", e);
        }

        course.setMedia(fileName);
        courseRepository.save(course);
    }




    private Course check_before_logic(int courseId, HttpServletRequest request)
    {
        Users loggedInInstructor = (Users) request.getSession().getAttribute("user");
        if (loggedInInstructor == null) {
            throw new IllegalArgumentException("No user is logged in.");
        }
        if (loggedInInstructor.getUserTypeId() == null || loggedInInstructor.getUserTypeId().getUserTypeId() != 3) {
            throw new IllegalArgumentException("Logged-in user is not an instructor.");
        }
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("No course found with the given ID: " + courseId));

        if (existingCourse.getInstructorId() == null ||
                existingCourse.getInstructorId().getUserAccountId() != loggedInInstructor.getUserId()) {
            throw new IllegalArgumentException("You are not authorized to update or delete this course.");
        }
        return existingCourse;
    }
    private List<CourseDto> convertToCourseDtoList(List<Course> courses) {
        return courses.stream()
                .map(course -> new CourseDto(
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getDescription(),
                        course.getDuration(),
                        course.getMedia(),
                        course.getInstructorId().getFirstName()
                ))
                .collect(Collectors.toList());
    }

    public void sendNotificationsToEnrolledStudents(int courseId, HttpServletRequest request){
        List<StudentDto> students = enrollmentService.viewEnrolledStudents(courseId,request);
        String message = getCourseById(courseId,request).getCourseName() + " course is updated";
        for (StudentDto student : students){
            notificationsService.sendNotification(message,student.getUserAccountId());
        }
    }


}
