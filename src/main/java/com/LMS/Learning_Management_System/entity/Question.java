package com.LMS.Learning_Management_System.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", referencedColumnName = "quiz_id")
    private Quiz quiz;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private QuestionType questionType;

    @Column(name = "options", columnDefinition = "json")
    private String options;

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course courseId;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    public Question() {}

    public Question(int questionId, Quiz quiz, String questionText, QuestionType questionType, String options, String correctAnswer) {
        this.questionId = questionId;
        this.quiz = quiz;
        this.questionText = questionText;
        this.questionType = questionType;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
    public Question( Quiz quiz, String questionText, QuestionType questionType, String options, String correctAnswer) {
        this.quiz = quiz;
        this.questionText = questionText;
        this.questionType = questionType;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Getters and Setters
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", quiz=" + quiz +
                ", questionText='" + questionText + '\'' +
                ", questionType=" + questionType +
                ", options='" + options + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
