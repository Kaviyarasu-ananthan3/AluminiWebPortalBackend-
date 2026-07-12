package com.alumni.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MentorshipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    private String studentName;
    private String alumniName;
    private String status;

    public MentorshipRequest() {
    }

    public MentorshipRequest(String topic, String studentName, String alumniName, String status) {
        this.topic = topic;
        this.studentName = studentName;
        this.alumniName = alumniName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getAlumniName() {
        return alumniName;
    }

    public String getStatus() {
        return status;
    }
}
