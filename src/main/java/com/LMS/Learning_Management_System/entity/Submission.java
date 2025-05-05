package com.LMS.Learning_Management_System.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")

    private int submissionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignment_id", referencedColumnName = "assignment_id")
    private Assignment assignmentId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "user_account_id")
    private Student studentId;

    @Column(name = "file_path")
    private String filePath;

    private Float grade;

    private String feedback;

    @Column(name = "submitted_at")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submittedAt;

    public Submission() {}

    public Submission(int submissionId, Assignment assignmentId, Student studentId, String filePath, Float grade, String feedback, Date submittedAt) {
        this.submissionId = submissionId;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.filePath = filePath;
        this.grade = grade;
        this.feedback = feedback;
        this.submittedAt = submittedAt;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public Assignment getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Assignment assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "submissionId=" + submissionId +
                ", assignmentId=" + assignmentId +
                ", studentId=" + studentId +
                ", filePath='" + filePath + '\'' +
                ", grade=" + grade +
                ", feedback='" + feedback + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
