package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.Admin;
import com.LMS.Learning_Management_System.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    public void save(Admin admin) {
        adminRepository.save(admin);
    }
}
