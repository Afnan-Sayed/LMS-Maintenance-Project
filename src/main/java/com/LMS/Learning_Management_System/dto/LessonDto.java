package com.LMS.Learning_Management_System.dto;


import java.util.Date;

public class LessonDto {
    private int lessonId;
    private int courseId;
    private String lessonName;
    private String lessonDescription;
    private int lessonOrder;
    private String OTP;
    private String content;
    private Date creationTime;
    public LessonDto() {

    }

    public LessonDto(int lessonId, int courseId , String lessonName, String lessonDescription, int lessonOrder, String OTP, String content, Date creationTime) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.lessonName = lessonName;
        this.lessonDescription = lessonDescription;
        this.lessonOrder = lessonOrder;
        this.OTP = OTP;
        this.content = content;
        this.creationTime = creationTime;
    }


    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }

    public int getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(int lessonOrder) {
        this.lessonOrder = lessonOrder;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
