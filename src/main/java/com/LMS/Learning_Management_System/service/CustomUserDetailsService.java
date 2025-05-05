package com.LMS.Learning_Management_System.service;


import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.repository.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service

//The CustomUserDetailsService class implements UserDetailsService,
// a Spring Security interface used to retrieve user data for authentication.
// This service is a bridge between Spring Security and your application's data source (in this case, UsersRepository)
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    //Wraps the Users object in a CustomUserDetails instance,
    // which adapts the Users entity to Spring Security’s UserDetails interface.
    // This allows Spring Security to recognize and use the user’s credentials and authorities during authentication.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException(email);
        }
        String userTypeName = user.getUserTypeId().getUserTypeName();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userTypeName.toUpperCase());

        // Return UserDetails with authorities
        return new User(user.getEmail(), user.getPassword(), Collections.singletonList(authority));
    }
}