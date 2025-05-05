DROP DATABASE IF EXISTS `LMS`;
CREATE DATABASE `LMS`;
USE `LMS`;

CREATE TABLE `users_type` (
  `user_type_id` int NOT NULL AUTO_INCREMENT,
  `user_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users_type` VALUES (1, 'ADMIN'), (2, 'STUDENT'), (3, 'INSTRUCTOR');

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `registration_date` datetime(6) DEFAULT NULL,
  `user_type_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FK5snet2ikvi03wd4rabd40ckdl` (`user_type_id`),
  CONSTRAINT `FK5snet2ikvi03wd4rabd40ckdl` FOREIGN KEY (`user_type_id`) REFERENCES `users_type` (`user_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users` (`user_id`, `email`, `password`, `user_type_id`) VALUES ('1', 'admin@example.com', '12345', '1');

CREATE TABLE `student` (
  `user_account_id` int NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_account_id`),
  CONSTRAINT `FK_student_user` FOREIGN KEY (`user_account_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `instructor` (
  `user_account_id` int NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_account_id`),
  CONSTRAINT `FK_instructor_user` FOREIGN KEY (`user_account_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `admin` (
  `user_account_id` int NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_account_id`),
  CONSTRAINT `FK_admin_user` FOREIGN KEY (`user_account_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) NOT NULL,
  `instructor_id` int NOT NULL,
  `description` text DEFAULT NULL,
  `media` varchar(255) DEFAULT null,
  `duration` int DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  CONSTRAINT `FK_course_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`user_account_id`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `lesson` (
  `lesson_id` INT NOT NULL AUTO_INCREMENT,
  `course_id` INT NOT NULL,
  `lesson_name` VARCHAR(255) NOT NULL,
  `lesson_description` TEXT DEFAULT NULL,
  `lesson_order` INT DEFAULT NULL, 
  `otp` VARCHAR(10) NOT NULL,
  `content` TEXT DEFAULT NULL, 
  `creation_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`lesson_id`),
  CONSTRAINT `FK_lesson_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)  ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enrollment` (
  `enrollment_id` int NOT NULL AUTO_INCREMENT,
  `student_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `enrollment_date`  DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`enrollment_id`),
  CONSTRAINT `FK_enrollment_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
  CONSTRAINT `FK_enrollment_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `assignment` (
  `assignment_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `course_id` int NOT NULL,
  `due_date`  DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`assignment_id`),
  CONSTRAINT `FK_assignment_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `submission` (
  `submission_id` int NOT NULL AUTO_INCREMENT,
  `assignment_id` int NOT NULL,
  `student_id` INT NOT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `grade` float DEFAULT NULL,
  `feedback` text DEFAULT NULL,
  `submitted_at`  DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`submission_id`),
  CONSTRAINT `FK_submission_assignment` FOREIGN KEY (`assignment_id`) REFERENCES `assignment` (`assignment_id`),
  CONSTRAINT `FK_submission_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `quiz` (
  `quiz_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `course_id` int NOT NULL,
  `question_count` int DEFAULT NULL,
  `randomized` BOOLEAN DEFAULT NULL,
  `creation_date`  DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`quiz_id`),
  CONSTRAINT `FK_quiz_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `grading` (
	`grade_id` int not null auto_increment,
    `grade` int not null,
    `quiz_id` int not null,
    `student_id` int not null,
    PRIMARY KEY (`grade_id`),
    CONSTRAINT `FK_grade_quiz` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`),
    CONSTRAINT `FK_grade_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_account_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `question_type` (
  `type_id` INT NOT NULL AUTO_INCREMENT,
  `type_name` ENUM('MCQ', 'TRUE_FALSE', 'SHORT_ANSWER') NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `question_type` (`type_name`) VALUES ('MCQ'), ('TRUE_FALSE'), ('SHORT_ANSWER');

CREATE TABLE `question` (
  `question_id` INT NOT NULL AUTO_INCREMENT,
  `quiz_id` INT NULL,
  `question_text` TEXT NOT NULL,
  `type_id` INT NOT NULL,
  `options` JSON DEFAULT NULL, 
  `course_id` int not null,
  `correct_answer` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`question_id`),
  CONSTRAINT `FK_question_quiz` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`),
  CONSTRAINT `FK_question_type` FOREIGN KEY (`type_id`) REFERENCES `question_type` (`type_id`),
  CONSTRAINT `FK_question_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `answers` (
  `answer_id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NOT NULL,
  `student_id` INT NOT NULL,
  `answer_text` TEXT NOT NULL,
  `is_correct` BOOLEAN DEFAULT NULL,
  PRIMARY KEY (`answer_id`),
  CONSTRAINT `FK_answer_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`),
  CONSTRAINT `FK_answer_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `notifications` (
  `notification_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `message` TEXT NOT NULL,
  `created_at`  DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notification_id`),
  CONSTRAINT `FK_notification_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `lesson_attendance` (
  `attendance_id` INT NOT NULL AUTO_INCREMENT,
  `lesson_id` INT NOT NULL,
  `student_id` INT NOT NULL,
  PRIMARY KEY (attendance_id),
  CONSTRAINT FK_lesson_attendance FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id) ON DELETE CASCADE,
  CONSTRAINT FK_student_attendance FOREIGN KEY (student_id) REFERENCES student (user_account_id) ON DELETE CASCADE);

ALTER TABLE notifications ADD COLUMN is_read BOOLEAN NOT NULL DEFAULT false;
