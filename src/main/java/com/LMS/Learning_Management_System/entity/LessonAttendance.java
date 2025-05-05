package com.LMS.Learning_Management_System.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lesson_attendance")
public class LessonAttendance  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private int attendanceId;

    @ManyToOne
    @JoinColumn(name = "lesson_id",referencedColumnName = "lesson_id")
    private Lesson lessonId;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_account_id")
    private Student studentId;

    public LessonAttendance() {

    }
    public LessonAttendance(int attendanceId, Lesson lessonId, Student studentId) {
        this.attendanceId = attendanceId;
        this.lessonId = lessonId;
        this.studentId = studentId;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Lesson getLessonId() {
        return lessonId;
    }

    public void setLessonId(Lesson lessonId) {
        this.lessonId = lessonId;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "LessonAttendance{" +
                "attendanceId=" + attendanceId +
                ", lessonId=" + lessonId +
                ", studentId=" + studentId +
                '}';
    }
}
