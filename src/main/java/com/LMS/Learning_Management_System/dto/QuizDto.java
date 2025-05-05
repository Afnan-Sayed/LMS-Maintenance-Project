package com.LMS.Learning_Management_System.dto;

import com.LMS.Learning_Management_System.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class QuizDto {

    private int quizId;

    private String title;

    private Date creation_date;
    private int type;

    private List<QuestionDto> questionList;

    private int course_id;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public List<QuestionDto> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionDto> questionList) {
        this.questionList = questionList;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public QuizDto(int quizId, String title, Date creation_date) {
        this.quizId = quizId;
        this.title = title;
        this.creation_date = creation_date;
        //this.questionList=questionList;
    }
    public QuizDto(){}

}
