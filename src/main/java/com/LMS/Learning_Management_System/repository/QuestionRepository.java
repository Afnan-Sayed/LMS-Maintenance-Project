package com.LMS.Learning_Management_System.repository;

import com.LMS.Learning_Management_System.dto.QuestionDto;
import com.LMS.Learning_Management_System.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("SELECT q FROM Question q WHERE q.courseId.courseId = :courseId")
    List<Question> findQuestionsByCourseId(@Param("courseId") int courseId);
    @Query("SELECT q FROM Question q WHERE q.quiz.quizId = :quizId")
    List<Question> findQuestionsByQuizId(@Param("quizId") int quizId);
    @Query("SELECT q FROM Question q WHERE q.courseId.courseId = :courseId AND q.questionType.typeId = :questionType")
    List<Question> findQuestionsByCourseIdAndQuestionType(@Param("courseId") int courseId, @Param("questionType") int questionType);
    @Query("SELECT q FROM Question q WHERE q.courseId.courseId = :courseId AND q.questionType.typeId = :questionType AND q.quiz.quizId IS NULL ")
    List<Question> findEmptyQuestionsByCourseIdAndQuestionType(@Param("courseId") int courseId, @Param("questionType") int questionType);
}
