package com.alumni.alumniconnectportal.controller;

import com.alumni.alumniconnectportal.entity.Alumni;
import com.alumni.alumniconnectportal.repository.AlumniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alumni")
@CrossOrigin(origins = "*")
public class AlumniController {

    @Autowired
    private AlumniRepository alumniRepository;


    // 🔥 GET ALL ALUMNI (Admin use)
    @GetMapping
    public List<Alumni> getAllAlumni() {
        return alumniRepository.findAll();
    }
}
