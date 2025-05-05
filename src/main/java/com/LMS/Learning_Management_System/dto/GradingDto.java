package com.LMS.Learning_Management_System.dto;

import com.LMS.Learning_Management_System.entity.Grading;

import java.util.List;

public class GradingDto {
    private int quiz_id;
    private List<String> answers ;
    private int student_id;
    private int grades;
    private List<Grading> allGrades;

    public List<Grading> getAllGrades() {
        return allGrades;
    }

    public void setAllGrades(List<Grading> allGrades) {
        this.allGrades = allGrades;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getGrades() {
        return grades;
    }

    public void setGrades(int grades) {
        this.grades = grades;
    }
}
