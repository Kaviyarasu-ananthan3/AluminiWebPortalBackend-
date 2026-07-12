package com.alumni.backend.bootstrap;

import com.alumni.backend.model.Alumni;
import com.alumni.backend.model.JobPost;
import com.alumni.backend.model.MentorshipRequest;
import com.alumni.backend.model.Student;
import com.alumni.backend.repository.AlumniRepository;
import com.alumni.backend.repository.JobPostRepository;
import com.alumni.backend.repository.MentorshipRequestRepository;
import com.alumni.backend.repository.StudentRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataSeeder {

    @Bean
    CommandLineRunner seedDemoData(
            AlumniRepository alumniRepository,
            StudentRepository studentRepository,
            JobPostRepository jobPostRepository,
            MentorshipRequestRepository mentorshipRequestRepository
    ) {
        return args -> {
            if (alumniRepository.count() == 0) {
                alumniRepository.saveAll(List.of(
                        new Alumni("Kavi Arasu", 2021, "Computer Science", "Zoho", "Backend Engineer", "Java, Spring Boot, MySQL", "Chennai"),
                        new Alumni("Nila Kumar", 2020, "Information Technology", "Freshworks", "Product Engineer", "React, APIs, Product Design", "Bengaluru"),
                        new Alumni("Arun Prakash", 2019, "Electronics", "TCS", "Cloud Consultant", "AWS, DevOps, SQL", "Hyderabad")
                ));
            }

            if (studentRepository.count() == 0) {
                studentRepository.saveAll(List.of(
                        new Student("Meena S", "Computer Science", 3, "Full-stack development"),
                        new Student("Rahul V", "Information Technology", 4, "Cloud internships"),
                        new Student("Divya P", "Electronics", 2, "Embedded systems")
                ));
            }

            if (jobPostRepository.count() == 0) {
                jobPostRepository.saveAll(List.of(
                        new JobPost("Junior Java Developer", "Zoho", "Chennai", "Referral", "Spring Boot and MySQL role for recent graduates."),
                        new JobPost("React Intern", "Freshworks", "Remote", "Internship", "Frontend internship for students comfortable with React.")
                ));
            }

            if (mentorshipRequestRepository.count() == 0) {
                mentorshipRequestRepository.saveAll(List.of(
                        new MentorshipRequest("Resume review", "Meena S", "Nila Kumar", "Scheduled"),
                        new MentorshipRequest("Java interview prep", "Rahul V", "Kavi Arasu", "Requested")
                ));
            }
        };
    }
}
