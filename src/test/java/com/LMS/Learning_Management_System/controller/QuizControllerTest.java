package com.LMS.Learning_Management_System.controller;

import com.LMS.Learning_Management_System.dto.GradingDto;
import com.LMS.Learning_Management_System.dto.QuestionDto;
import com.LMS.Learning_Management_System.dto.QuizDto;
import com.LMS.Learning_Management_System.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
    }
    @Test
    void testGetQuizById(){

        QuizDto quizDto = new QuizDto();
        when(quizService.getQuizByID(eq(1),eq(request))).thenReturn(quizDto);

        ResponseEntity<?> response = quizController.getQuizById(1, request);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(quizDto,response.getBody());
        verify(quizService, times(1)).getQuizByID(eq(1), eq(request));
    }
    @Test
    void testGetActiveQuiz(){
        String ans = "quiz with id: "+1+" has time left: "+5+"\n";
        when(quizService.getActiveQuiz(eq(1),eq(request))).thenReturn(ans);

        ResponseEntity<?> response = quizController.getActiveQuiz(1, request);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(ans,response.getBody());
        verify(quizService, times(1)).getActiveQuiz(eq(1), eq(request));
    }
    @Test
    void testAddQuiz() throws Exception {
        String ans = "Quiz created successfully. Use this id: "+1+" to enter the quiz";
        QuizDto quizDto = new QuizDto();
        quizDto.setQuizId(1);
        quizDto.setCourse_id(1);
        quizDto.setType(1);
        when(quizService.Create(eq(1),eq(1),eq(request))).thenReturn(1);

        ResponseEntity<?> response = quizController.addQuiz(quizDto, request);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(ans,response.getBody());
        verify(quizService, times(1)).Create(eq(1),eq(1), eq(request));
    }
    @Test
    void testAddQuestionsBank() throws Exception {
        String ans = "Question bank created successfully for the course id: "+1;
        List<QuestionDto> questions = new ArrayList<>();
        QuizDto quizDto = new QuizDto();
        quizDto.setQuizId(1);
        quizDto.setCourse_id(1);
        quizDto.setType(1);
        quizDto.setQuestionList(questions);
        doNothing().when(quizService).createQuestionBank(1,questions,request);

        ResponseEntity<?> response = quizController.addQuestionsBank(quizDto, request);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(ans,response.getBody());
        verify(quizService, times(1)).createQuestionBank(eq(1),eq(questions), eq(request));
    }
    @Test
    void testAddQuestions() throws Exception {
        String ans = "Question added successfully for the course id: "+1;
        QuestionDto questionDto = new QuestionDto();
        questionDto.setCourse_id(1);
        doNothing().when(quizService).addQuestion(questionDto,request);

        ResponseEntity<?> response = quizController.addQuestions(questionDto, request);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(ans,response.getBody());
        verify(quizService, times(1)).addQuestion(eq(questionDto), eq(request));
    }
    @Test
    void testGetQuestionBank() throws Exception {

        List<QuestionDto> questions = new ArrayList<>();
        QuizDto quizDto = new QuizDto();
        quizDto.setQuizId(1);
        quizDto.setCourse_id(1);
        quizDto.setType(1);
        quizDto.setQuestionList(questions);
        when(quizService.getQuestionBank(eq(1),eq(request))).thenReturn(quizDto);

        ResponseEntity<?> response = quizController.getQuestionBank(1, request);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(questions,response.getBody());
        verify(quizService, times(1)).getQuestionBank(eq(1), eq(request));
    }
    @Test
    void testGradeQuiz() throws Exception {

        GradingDto gradingDto = new GradingDto();
        doNothing().when(quizService).gradeQuiz(eq(gradingDto),eq(request));

        ResponseEntity<?> response = quizController.gradeQuiz(gradingDto, request);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals("Quiz has been graded for the student",response.getBody());
        verify(quizService, times(1)).gradeQuiz(eq(gradingDto), eq(request));
    }
    @Test
    void testGetQuizGradeByStudent() throws Exception {
        int ans = 10;

        when(quizService.quizFeedback(1,1,request)).thenReturn(ans);

        ResponseEntity<?> response = quizController.getQuizGradeByStudent(1,1, request);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(ans,response.getBody());
        verify(quizService, times(1)).quizFeedback(eq(1),eq(1), eq(request));
    }
    @Test
    void testGetQuizQuestions() throws Exception {
        List<QuestionDto>ans = new ArrayList<QuestionDto>();

        when(quizService.getQuizQuestions(eq(1),eq(request))).thenReturn(ans);

        ResponseEntity<?> response = quizController.getQuizQuestions(1, request);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(ans,response.getBody());
        verify(quizService, times(1)).getQuizQuestions(eq(1), eq(request));
    }

    @Test
    void testTrackQuizGrades()
    {
        List <String> ans = new ArrayList <String>();

        when(quizService.quizGrades(eq(1), eq(request))).thenReturn(ans);

        ResponseEntity <?> response = quizController.trackQuizGrades(1, request);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(ans, response.getBody());
        verify(quizService, times(1)).quizGrades(eq(1), eq(request));
    }
}
