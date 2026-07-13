package com.alumni.alumniconnectportal.repository;

import com.alumni.alumniconnectportal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmailAndPassword(String email, String password);

    Student findByEmail(String email);

}