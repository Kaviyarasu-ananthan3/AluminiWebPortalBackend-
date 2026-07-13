package com.alumni.alumniconnectportal.controller;

import com.alumni.alumniconnectportal.repository.UserRepository;
import com.alumni.alumniconnectportal.repository.MentorshipRepository;
import com.alumni.alumniconnectportal.repository.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentorshipRepository mentorshipRepository;

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/stats")
    public Map<String, Long> getStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("students", userRepository.countByRole("student"));
        stats.put("alumni", userRepository.countByRole("alumni"));
        stats.put("jobs", jobRepository.count());
        stats.put("mentors", mentorshipRepository.count());

        return stats;
    }
}