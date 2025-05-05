package com.LMS.Learning_Management_System.controller;

import com.LMS.Learning_Management_System.dto.AssignmentDto;
import com.LMS.Learning_Management_System.entity.Assignment;
import com.LMS.Learning_Management_System.entity.Student;
import com.LMS.Learning_Management_System.entity.Submission;
import com.LMS.Learning_Management_System.service.AssignmentService;
import com.LMS.Learning_Management_System.service.NotificationsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/assignment")
public class AssigmentController {
    private final AssignmentService assignmentService;
    private final NotificationsService notificationsService;

    public AssigmentController(AssignmentService assignmentService, NotificationsService notificationsService) {
        this.assignmentService = assignmentService;
        this.notificationsService = notificationsService;
    }

    @PostMapping("/uploadAssignment")
    public ResponseEntity<String> uploadAssignment(@RequestBody AssignmentDto assignment, HttpServletRequest request){
        try {
            assignmentService.uploadAssignment(assignment, request);
            return ResponseEntity.ok("Assignment uploaded successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/gradeAssignment/studentID={studentID}/assigID={assigID}/grade={grade}")
    public ResponseEntity<String> gradeAssignment(@PathVariable int studentID, @PathVariable int assigID, @PathVariable float grade, HttpServletRequest request ){
        try {
            assignmentService.gradeAssignment(studentID, assigID, grade, request);
            String message = "Assignment "+assigID+" grade is uploaded";
            notificationsService.sendNotification(message,studentID);
            return ResponseEntity.ok("Assignment has been graded successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/saveAssignmentFeedback/studentID={studentID}/assigID={assigID}/feedback={feedback}")
    public ResponseEntity<String> saveAssignmentFeedback(@PathVariable int studentID, @PathVariable int assigID, @PathVariable String feedback, HttpServletRequest request ){
        try {
            assignmentService.saveAssignmentFeedback(studentID, assigID, feedback, request);
            return ResponseEntity.ok("Assignment feedback is saved successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getFeedback/assignmentId={assignmentId}")
    public ResponseEntity<String> getFeedback(@PathVariable int assignmentId, HttpServletRequest request){
        try {
            return ResponseEntity.ok(assignmentService.getFeedback(assignmentId, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/submissions/{assignmentId}")
    public ResponseEntity <List <String>> trackAssignmentSubmissions (@PathVariable int assignmentId, HttpServletRequest request)
    {
        try
        {
            List <String> submissions = assignmentService.assignmentSubmissions(assignmentId, request);
            return ResponseEntity.ok(submissions);
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
        }
    }
}
