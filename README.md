Learning Management System (LMS)






A web-based Learning Management System (LMS) built using Java Spring Boot for managing online courses, assessments, and performance tracking. The system supports multiple user roles (Admin, Instructor, Student) with features like course creation, user management, quizzes, assignments, and more.

Features
1. User Management
Role-Based Access Control: Supports Admin, Instructor, and Student roles.
User Registration & Login: Secure login with role-based access.
Profile Management: Allows users to view and update their profiles.
2. Course Management
Course Creation: Instructors can create and manage courses with media files (videos, PDFs, etc.).
Enrollment Management: Students can enroll in courses; Admins and Instructors can manage enrollments.
Attendance Management: Instructors generate OTPs for lessons to track student attendance.
3. Assessments & Grading
Quiz Creation: Instructors create quizzes with various question types (MCQs, True/False, Short Answer).
Assignment Submission: Students submit assignments for grading.
Grading & Feedback: Instructors grade assignments and provide feedback to students.
4. Performance Tracking
Student Progress: Track student performance through quiz scores, assignment submissions, and attendance.
5. Notifications
System Notifications: Students and Instructors receive notifications for enrollment, grades, and course updates.
Email Notifications: Students get email updates for course enrollments and assignment grades.
6. Bonus Features
Excel Report Generation: Generate performance reports for students.
Visual Analytics: Charts to visualize student progress and course completion.
Technical Stack
Backend: Java Spring Boot (RESTful APIs)
Database: MySQL (or PostgreSQL)
Authentication: Spring Security for role-based access
Email: JavaMailSender for email notifications
Testing: JUnit for unit testing
Excel Reports: Apache POI for Excel generation
Build Tool: Maven
Getting Started
Prerequisites
Java 17 or later
MySQL or PostgreSQL database
Maven for project management
IDE (e.g., IntelliJ IDEA, Eclipse)
Installation
Clone the repository:

bash
Copy code
git clone https://github.com/yourusername/Learning_Management_System.git
Navigate to the project directory:

bash
Copy code
cd Learning_Management_System
Set up the database and configure application.properties with your database credentials.

Build and run the application:

bash
Copy code
mvn spring-boot:run
Access the application at http://localhost:8080.

Contributing
Fork the repository.
Create a new branch (git checkout -b feature-branch).
Commit your changes (git commit -am 'Add new feature').
Push to the branch (git push origin feature-branch).
Create a new Pull Request.
License
