package com.alumni.alumniconnectportal.controller;

import com.alumni.alumniconnectportal.model.JobApplication;
import com.alumni.alumniconnectportal.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/jobApplication")
@CrossOrigin(origins = "*")
public class JobApplicationController {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    // ==============================
    // Student Apply Job
    // ==============================

    @PostMapping("/apply")
    public String applyJob(

            @RequestParam("jobId") Long jobId,
            @RequestParam("studentName") String studentName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("department") String department,
            @RequestParam("cgpa") String cgpa,
            @RequestParam("skills") String skills,

            @RequestParam("resume") MultipartFile resume,
            @RequestParam("coverLetter") MultipartFile coverLetter

    ) {

        try {

            String uploadDir = "uploads";

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String resumeFileName =
                    System.currentTimeMillis() + "_" + resume.getOriginalFilename();

            String coverFileName =
                    System.currentTimeMillis() + "_" + coverLetter.getOriginalFilename();

            Files.copy(
                    resume.getInputStream(),
                    uploadPath.resolve(resumeFileName),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            Files.copy(
                    coverLetter.getInputStream(),
                    uploadPath.resolve(coverFileName),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            JobApplication application = new JobApplication();

            application.setJobId(jobId);
            application.setStudentName(studentName);
            application.setEmail(email);
            application.setPhone(phone);
            application.setDepartment(department);
            application.setCgpa(cgpa);
            application.setSkills(skills);

            application.setResume(resumeFileName);
            application.setCoverLetter(coverFileName);

            application.setStatus("Pending");

            jobApplicationRepository.save(application);

            return "Application Submitted Successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "Upload Failed : " + e.getMessage();

        }

    }

    // ==============================
    // Alumni View Applicants
    // ==============================

    @GetMapping("/job/{jobId}")
    public List<JobApplication> getApplicants(
            @PathVariable Long jobId) {

        return jobApplicationRepository.findByJobId(jobId);

    }

    // ==============================
    // Student View Applications
    // ==============================

    @GetMapping("/student/{studentName}")
    public List<JobApplication> getStudentApplications(
            @PathVariable String studentName) {

        return jobApplicationRepository.findByStudentName(studentName);

    }

    // ==============================
    // Recommend to HR
    // ==============================

    @PutMapping("/accept/{id}")
    public String acceptApplicant(@PathVariable Long id) {

        JobApplication application =
                jobApplicationRepository.findById(id).orElse(null);

        if (application == null) {
            return "Application Not Found";
        }

        application.setStatus("Recommended to HR");

        jobApplicationRepository.save(application);

        return "Candidate Recommended to HR";
    }

    // ==============================
    // Reject Applicant
    // ==============================

    @PutMapping("/reject/{id}")
    public String rejectApplicant(@PathVariable Long id) {

        JobApplication application =
                jobApplicationRepository.findById(id).orElse(null);

        if (application == null) {
            return "Application Not Found";
        }

        application.setStatus("Rejected");

        jobApplicationRepository.save(application);

        return "Applicant Rejected";
    }

    // ==============================
    // Download Resume
    // ==============================

    @GetMapping("/resume/{fileName}")
    public byte[] downloadResume(
            @PathVariable String fileName) throws Exception {

        Path path = Paths.get("uploads").resolve(fileName);

        return Files.readAllBytes(path);
    }

    // ==============================
    // Download Cover Letter
    // ==============================

    @GetMapping("/cover/{fileName}")
    public byte[] downloadCover(
            @PathVariable String fileName) throws Exception {

        Path path = Paths.get("uploads").resolve(fileName);

        return Files.readAllBytes(path);
    }

}