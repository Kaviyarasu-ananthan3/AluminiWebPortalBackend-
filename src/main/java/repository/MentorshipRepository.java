package com.alumni.alumniconnectportal.repository;

import com.alumni.alumniconnectportal.model.Mentorship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorshipRepository extends JpaRepository<Mentorship, Long> {

    // Mentor Requests
    List<Mentorship> findByMentorName(String mentorName);

    // ⭐ Student Requests
    List<Mentorship> findByStudentName(String studentName);
    List<Mentorship> findByStudentNameAndStatusNot(String studentName, String status);
    List<Mentorship> findByStudentNameAndIsReadFalse(String studentName);

    long countByStudentName(String studentName);

    long countByStudentNameAndStatus(String studentName,String status);


}