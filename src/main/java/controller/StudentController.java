package com.alumni.alumniconnectportal.controller;

import com.alumni.alumniconnectportal.entity.Student;
import com.alumni.alumniconnectportal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    @PostMapping("/login")
    public Student login(@RequestBody Student student) {

        return studentRepository.findByEmailAndPassword(
                student.getEmail(),
                student.getPassword()
        );
    }


}
