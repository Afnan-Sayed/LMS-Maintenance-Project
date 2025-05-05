package com.LMS.Learning_Management_System.controller;

import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.entity.UsersType;
import com.LMS.Learning_Management_System.service.UsersService;
import com.LMS.Learning_Management_System.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//
//@RequestMapping("/api/users")
// public ResponseEntity<List<UsersType>> getAllUserTypes() {
//    List<UsersType> usersTypes = usersTypeService.getAll();
//    return ResponseEntity.ok(usersTypes);
//}


//}