package com.alumni.alumniconnectportal.repository;

import com.alumni.alumniconnectportal.entity.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumniRepository extends JpaRepository<Alumni, Long> {

    Alumni findByEmailAndPassword(String email, String password);

    Alumni findByEmail(String email);

}