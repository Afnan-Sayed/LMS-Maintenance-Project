package com.LMS.Learning_Management_System.repository;

import com.LMS.Learning_Management_System.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);

}
