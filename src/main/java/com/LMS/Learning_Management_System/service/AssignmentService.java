package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.dto.AssignmentDto;
import com.LMS.Learning_Management_System.entity.*;
import com.LMS.Learning_Management_System.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final InstructorRepository instructorRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, SubmissionRepository submissionRepository,
                             CourseRepository courseRepository, StudentRepository studentRepository,
                             EnrollmentRepository enrollmentRepository, InstructorRepository instructorRepository) {
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.instructorRepository = instructorRepository;
    }

    @Transactional
    public void uploadAssignment(AssignmentDto assignment, HttpServletRequest request) {
        Users loggedInInstructor = (Users) request.getSession().getAttribute("user");
        if (loggedInInstructor == null) {
            throw new IllegalArgumentException("You are not logged in");
        }

        Course course = courseRepository.findById(assignment.getCourseId())
                .orElseThrow(()-> new IllegalArgumentException("Course not found"));

        Instructor instructor = instructorRepository.findById(loggedInInstructor.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("You're not an instructor"));

//        Assignment assignment1 = new Assignment();
//        assignment1.setTitle(assignment.getAssignmentTitle());
//        assignment1.setDescription(assignment.getAssignmentDescription());
//        assignment1.setCourseID(course);
//        assignmentRepository.save(assignment1);
    }


    public void gradeAssignment(int studentID, int assigID, float grade, HttpServletRequest request ) {
        Users loggedInInstructor = (Users) request.getSession().getAttribute("user");
        if (loggedInInstructor == null) {
            throw new IllegalArgumentException("You are not logged in");
        }

        Assignment assignment = assignmentRepository.findById(assigID)
                .orElseThrow(()-> new IllegalArgumentException("Assignment not found"));


        if (loggedInInstructor.getUserId() != assignment.getCourseID().getInstructorId().getUserAccountId()){
            throw new IllegalArgumentException("You're not the instructor of this course");
        }


        Student student = studentRepository.findById(studentID)
                .orElseThrow(()-> new IllegalArgumentException("Student not found"));

        List<Submission> submission = submissionRepository.findByStudentId(student);


        if (submission.isEmpty()) {
            throw new IllegalArgumentException("Student has no submissions");
        }

        for (Submission s : submission) {
            if (s.getAssignmentId().getAssignmentId() == assignment.getAssignmentId()) {
                s.setGrade(grade);
                submissionRepository.save(s);
                return;
            }
        }
        throw new IllegalArgumentException("Student didn't submit this assignment");

    }

    public void saveAssignmentFeedback(int studentID, int assigID, String feedback, HttpServletRequest request) {
        Users loggedInInstructor = (Users) request.getSession().getAttribute("user");
        if (loggedInInstructor == null) {
            throw new IllegalArgumentException("You are not logged in");
        }


        Assignment assignment = assignmentRepository.findById(assigID)
                .orElseThrow(()-> new IllegalArgumentException("Assignment not found"));


        if (loggedInInstructor.getUserId() != assignment.getCourseID().getInstructorId().getUserAccountId()){
            throw new IllegalArgumentException("You're not the instructor of this course");
        }


        Student student = studentRepository.findById(studentID)
                .orElseThrow(()-> new IllegalArgumentException("Student not found"));

        List<Submission> submission = submissionRepository.findByStudentId(student);


        if (submission.isEmpty()) {
            throw new IllegalArgumentException("Student has no submissions");
        }

        for (Submission s : submission) {
            if (s.getAssignmentId().getAssignmentId() == assignment.getAssignmentId()) {
                s.setFeedback(feedback);
                submissionRepository.save(s);
                return;
            }
        }
        throw new IllegalArgumentException("Student didn't submit this assignment");
    }

    public String getFeedback(int assigID, HttpServletRequest request) {
        Users loggedInStudent = (Users) request.getSession().getAttribute("user");
        if (loggedInStudent == null) {
            throw new IllegalArgumentException("You are not logged in");
        }

        Assignment assignment = assignmentRepository.findById(assigID)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        Student student = studentRepository.findById(loggedInStudent.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("You're not a student"));

        boolean isEnrolled = enrollmentRepository.existsByStudentAndCourse(student, assignment.getCourseID());
        if (!isEnrolled) {
            throw new IllegalArgumentException("You're not enrolled in this course");
        }

        List<Submission> submissions = submissionRepository.findByStudentId(student);

        for (Submission submission : submissions) {
            if (submission.getAssignmentId().getAssignmentId() == assignment.getAssignmentId()) {
                return (submission.getFeedback() != null) ? submission.getFeedback() : "There is no feedback yet";
            }
        }

        throw new IllegalArgumentException("Student didn't submit this assignment");
    }

    // New Feature -> lead to change in submissionRepository to add this --> "existsByStudentAndAssignment(student, assignment)"

    public boolean isAssignmentSubmitted(int assignmentId, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
            throw new IllegalArgumentException("You are not logged in");
        }

        Student student = studentRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("You are not a student"));

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        return submissionRepository.existsByStudentIdAndAssignmentId(student, assignment);
    }

    public List <String> assignmentSubmissions (int assignmentId, HttpServletRequest request)
    {
        if (assignmentRepository.existsById(assignmentId))
        {
            Assignment assignment = assignmentRepository.findById(assignmentId).get();
            List <Submission> assignmentSubmissions = submissionRepository.findAllByAssignmentId(assignment);
            Users loggedInInstructor = (Users) request.getSession().getAttribute("user");
            int instructorId = assignment.getCourseID().getInstructorId().getUserAccountId();

            if (loggedInInstructor == null)
            {
                throw new IllegalArgumentException("No logged in user is found.");
            }
            else if (loggedInInstructor.getUserTypeId() == null || loggedInInstructor.getUserTypeId().getUserTypeId() != 3)
            {
                throw new IllegalArgumentException("Logged-in user is not an instructor.");
            }
            else if (instructorId != loggedInInstructor.getUserId())
            {
                throw new IllegalArgumentException("Logged-in instructor does not have access for this assignment submissions.");
            }

            List <String> submissions = new ArrayList<>();
            for (Submission submission : assignmentSubmissions)
            {
                Student student = submission.getStudentId();
                String studentSubmission = student.getUserAccountId() + ": " + submission.getFilePath();
                submissions.add(studentSubmission);
            }
            return submissions;
        }
        else
        {
            throw new IllegalArgumentException("Assignment with ID " + assignmentId + " not found.");
        }
    }

    public void deleteAssignment (int assigID, HttpServletRequest request)
    {
        Users loggedInInstructor = (Users) request.getSession().getAttribute("user");
        if (loggedInInstructor == null) {
            throw new IllegalArgumentException("You are not logged in");
        }

        Assignment assignment = assignmentRepository.findById(assigID)
                .orElseThrow(()-> new IllegalArgumentException("Assignment not found"));

        if (loggedInInstructor.getUserId() != assignment.getCourseID().getInstructorId().getUserAccountId()){
            throw new IllegalArgumentException("You're not the instructor of this course");
        }

        assignmentRepository.delete(assignment);

        throw new IllegalArgumentException("There is no assigment with this id");
    }

}
