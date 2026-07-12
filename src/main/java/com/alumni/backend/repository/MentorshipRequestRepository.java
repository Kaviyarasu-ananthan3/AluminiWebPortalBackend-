package com.alumni.backend.repository;

import com.alumni.backend.model.MentorshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {
}
