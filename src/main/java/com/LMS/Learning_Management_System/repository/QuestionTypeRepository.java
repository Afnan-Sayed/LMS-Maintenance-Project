package com.LMS.Learning_Management_System.repository;

import com.LMS.Learning_Management_System.entity.QuestionType;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Integer> {
}
