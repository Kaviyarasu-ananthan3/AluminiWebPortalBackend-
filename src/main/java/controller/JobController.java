package com.alumni.alumniconnectportal.controller;

import com.alumni.alumniconnectportal.model.Job;
import com.alumni.alumniconnectportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // Alumni Post Job
    @PostMapping("/add")
    public String addJob(@RequestBody Job job) {
        jobRepository.save(job);
        return "Job Posted Successfully";
    }

    // Students View Jobs
    @GetMapping("/all")
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Delete Job
    @DeleteMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) {

        jobRepository.deleteById(id);

        return "Job Deleted Successfully";
    }

    // Get One Job (Future Edit use)
    @GetMapping("/{id}")
    public Job getJob(@PathVariable Long id) {

        return jobRepository.findById(id).orElse(null);
    }

    @GetMapping("/myjobs/{postedBy}")
    public List<Job> getMyJobs(@PathVariable String postedBy) {
        return jobRepository.findByPostedBy(postedBy);
    }
}

