package com.alumni.alumniconnectportal.repository;

import com.alumni.alumniconnectportal.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByStudentNameAndMentorNameOrderByDateTimeAsc(
            String studentName,
            String mentorName
    );

}