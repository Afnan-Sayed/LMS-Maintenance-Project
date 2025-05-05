package com.LMS.Learning_Management_System.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")

    private int quizId;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

    @Column(name = "question_count")
    private Integer questionCount;

    @Column(name = "randomized")
    private Boolean randomized;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    public Quiz() {}

    public Quiz(int quizId, String title, Course course, Integer questionCount, Boolean randomized, Date creationDate) {
        this.quizId = quizId;
        this.title = title;
        this.course = course;
        this.questionCount = questionCount;
        this.randomized = randomized;
        this.creationDate = creationDate;
    }
    public Quiz( String title, Course course, Integer questionCount, Boolean randomized, Date creationDate) {
        this.title = title;
        this.course = course;
        this.questionCount = questionCount;
        this.randomized = randomized;
        this.creationDate = creationDate;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Boolean getRandomized() {
        return randomized;
    }

    public void setRandomized(Boolean randomized) {
        this.randomized = randomized;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", title='" + title + '\'' +
                ", course=" + course +
                ", questionCount=" + questionCount +
                ", randomized=" + randomized +
                ", creationDate=" + creationDate +
                '}';
    }
}
