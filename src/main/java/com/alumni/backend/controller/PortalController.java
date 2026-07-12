package com.alumni.backend.controller;

import com.alumni.backend.model.Alumni;
import com.alumni.backend.model.JobPost;
import com.alumni.backend.model.MentorshipRequest;
import com.alumni.backend.model.Student;
import com.alumni.backend.repository.AlumniRepository;
import com.alumni.backend.repository.JobPostRepository;
import com.alumni.backend.repository.MentorshipRequestRepository;
import com.alumni.backend.repository.StudentRepository;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PortalController {

    private final AlumniRepository alumniRepository;
    private final StudentRepository studentRepository;
    private final JobPostRepository jobPostRepository;
    private final MentorshipRequestRepository mentorshipRequestRepository;

    public PortalController(
            AlumniRepository alumniRepository,
            StudentRepository studentRepository,
            JobPostRepository jobPostRepository,
            MentorshipRequestRepository mentorshipRequestRepository
    ) {
        this.alumniRepository = alumniRepository;
        this.studentRepository = studentRepository;
        this.jobPostRepository = jobPostRepository;
        this.mentorshipRequestRepository = mentorshipRequestRepository;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "ok",
                "service", "alumni-connect-backend",
                "time", Instant.now().toString()
        );
    }

    @GetMapping("/alumni")
    public List<Alumni> alumni() {
        return alumniRepository.findAll();
    }

    @GetMapping("/students")
    public List<Student> students() {
        return studentRepository.findAll();
    }

    @GetMapping("/jobs")
    public List<JobPost> jobs() {
        return jobPostRepository.findAll();
    }

    @PostMapping("/jobs")
    public JobPost createJob(@RequestBody JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    @GetMapping("/mentorship")
    public List<MentorshipRequest> mentorship() {
        return mentorshipRequestRepository.findAll();
    }

    @PostMapping("/mentorship")
    public MentorshipRequest createMentorship(@RequestBody MentorshipRequest request) {
        return mentorshipRequestRepository.save(request);
    }
}
