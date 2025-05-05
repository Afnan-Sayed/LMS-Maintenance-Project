package com.LMS.Learning_Management_System.dto;

// this Dto for get course by , id , we send the important data for course not all course data , that is the data transfer object

public class CourseDto {
    private int courseId;
    private String courseName;
    private String description;
    private int duration;
    private String instructorName;
    private String media;

    public CourseDto() {
    }

    public CourseDto(int courseId, String courseName, String description, int duration, String media ,String instructorName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.duration = duration;
        this.media = media;
        this.instructorName = instructorName;

    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getInstructorName() {
        return instructorName;
    }
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
    public String getMedia() {
        return media;
    }
    public void setMedia(String media) {
        this.media = media;
    }
}
