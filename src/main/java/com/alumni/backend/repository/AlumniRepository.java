package com.alumni.backend.repository;

import com.alumni.backend.model.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumniRepository extends JpaRepository<Alumni, Long> {
}
