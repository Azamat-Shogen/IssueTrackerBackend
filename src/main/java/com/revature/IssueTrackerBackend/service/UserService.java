package com.revature.IssueTrackerBackend.service;

import com.revature.IssueTrackerBackend.entity.User;
import com.revature.IssueTrackerBackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
