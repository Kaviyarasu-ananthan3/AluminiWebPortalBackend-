package com.alumni.alumniconnectportal.repository;

import com.alumni.alumniconnectportal.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByJobId(Long jobId);

    List<JobApplication> findByStudentName(String studentName);

}
