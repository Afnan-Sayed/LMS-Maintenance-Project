package com.LMS.Learning_Management_System.repository;

import com.LMS.Learning_Management_System.entity.Enrollment;
import com.LMS.Learning_Management_System.entity.Grading;
import com.LMS.Learning_Management_System.entity.Question;
import com.LMS.Learning_Management_System.entity.Quiz;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface GradingRepository extends JpaRepository<Grading, Integer> {
    @Query("SELECT COALESCE(g.grade, -1) FROM Grading g WHERE g.quizId.quizId = :quizId AND g.student_id.userId.userId = :studentId")
    int findGradeByQuizAndStudentID(@Param("quizId") int quizId, @Param("studentId") int studentId);
    @Query("SELECT COUNT(g)>0 "+
            "FROM Grading g " +
            "WHERE g.quizId.quizId = :quizId AND g.student_id.userId.userId = :studentId")
    Optional<Boolean> boolFindGradeByQuizAndStudentID(@Param("quizId") int quizId, @Param("studentId") int studentId);
    @Query("SELECT g.student_id.userAccountId,g.grade FROM Grading g WHERE g.quizId.quizId = :quizId")
    Map<Integer,Integer> findGradeByQuiz(@Param("quizId") int quizId);
    @Query("SELECT g.student_id.userAccountId FROM Grading g WHERE g.quizId.quizId = :quizId")
    List<Integer> findStudentByQuiz(@Param("quizId") int quizId);
    @Query("SELECT g.grade FROM Grading g WHERE g.quizId.quizId = :quizId")
    List<Integer> findGradeByQuizId(@Param("quizId") int quizId);
    List <Grading> findAllByQuizId (Quiz quizId);
}
