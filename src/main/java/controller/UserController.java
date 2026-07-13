package com.alumni.alumniconnectportal.controller;

import com.alumni.alumniconnectportal.model.User;
import com.alumni.alumniconnectportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get only alumni
    @GetMapping("/alumni")
    public List<User> getAllAlumni() {
        return userRepository.findByRole("alumni");
    }

    // Get only students
    @GetMapping("/students")
    public List<User> getAllStudents() {
        return userRepository.findByRole("student");
    }
}