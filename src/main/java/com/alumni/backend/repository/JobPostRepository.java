package com.alumni.backend.repository;

import com.alumni.backend.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
