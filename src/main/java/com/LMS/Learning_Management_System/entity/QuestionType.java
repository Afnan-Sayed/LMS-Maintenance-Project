package com.LMS.Learning_Management_System.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question_type")
public class QuestionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int typeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_name", nullable = false)
    private QuestionTypeEnum typeName;

    public QuestionType() {}

    public QuestionType(int typeId, QuestionTypeEnum typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }


    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public QuestionTypeEnum getTypeName() {
        return typeName;
    }

    public void setTypeName(QuestionTypeEnum typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "QuestionType{" +
                "typeId=" + typeId +
                ", typeName=" + typeName +
                '}';
    }

    public enum QuestionTypeEnum {
        MCQ, TRUE_FALSE, SHORT_ANSWER;
    }
}
