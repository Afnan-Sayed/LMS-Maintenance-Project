package com.LMS.Learning_Management_System.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;

    private String courseName;

    // when delete course didnot delete instructor
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "instructor_id", referencedColumnName = "user_account_id")
    private Instructor instructorId;

    private String description;

    @Column(name = "media", nullable = true, length = 64)
    private String media;

    private int duration;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    @OneToMany(mappedBy = "courseId")
    private List<Lesson> lessons;
    public Course() {

    }

    public Course(int courseId, String courseName, Instructor instructorId, String description, String media, int duration, Date creationDate) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructorId = instructorId;
        this.description = description;
        this.media = media;
        this.duration = duration;
        this.creationDate = creationDate;
        this.lessons = lessons;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Instructor getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Instructor instructorId) {
        this.instructorId = instructorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", instructorId=" + instructorId +
                ", description='" + description + '\'' +
                ", media='" + media + '\'' +
                ", duration=" + duration +
                ", creationDate=" + creationDate +
                '}';
    }
}
