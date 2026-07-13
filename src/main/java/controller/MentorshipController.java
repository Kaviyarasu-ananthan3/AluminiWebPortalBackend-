package com.alumni.alumniconnectportal.controller;

import com.alumni.alumniconnectportal.model.Mentorship;
import com.alumni.alumniconnectportal.repository.MentorshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/mentorship")
@CrossOrigin(origins = "*")
public class MentorshipController {

    @Autowired
    private MentorshipRepository mentorshipRepository;

    // Student sends mentorship request
    @PostMapping("/request")
    public String requestMentorship(@RequestBody Mentorship mentorship) {

        mentorship.setStatus("Pending");

        mentorshipRepository.save(mentorship);

        return "Mentorship Request Sent Successfully";
    }

    // Admin / Testing - View all requests
    @GetMapping("/all")
    public List<Mentorship> getAllRequests() {

        return mentorshipRepository.findAll();
    }

    // Logged-in mentor sees only his/her requests
    @GetMapping("/mentor/{mentorName}")
    public List<Mentorship> getMentorRequests(
            @PathVariable String mentorName) {

        return mentorshipRepository.findByMentorName(mentorName);
    }

    // ⭐ NEW - Student sees only his/her requests
    @GetMapping("/student/{studentName}")
    public List<Mentorship> getStudentRequests(
            @PathVariable String studentName) {

        return mentorshipRepository.findByStudentName(studentName);
    }

    @GetMapping("/student/dashboard/{studentName}")
    public Map<String, Long> getDashboardCounts(
            @PathVariable String studentName) {

        Map<String, Long> data = new HashMap<>();

        data.put("total",
                mentorshipRepository.countByStudentName(studentName));

        data.put("pending",
                mentorshipRepository.countByStudentNameAndStatus(studentName, "Pending"));

        data.put("accepted",
                mentorshipRepository.countByStudentNameAndStatus(studentName, "Accepted"));

        data.put("rejected",
                mentorshipRepository.countByStudentNameAndStatus(studentName, "Rejected"));

        return data;
    }
    @GetMapping("/notification/{studentName}")
    public List<Mentorship> getNotifications(
            @PathVariable String studentName) {

        return mentorshipRepository.findByStudentNameAndIsReadFalse(studentName);
    }


    // Accept / Reject request
    @PutMapping("/update/{id}/{status}")
    public String updateStatus(
            @PathVariable Long id,
            @PathVariable String status) {

        Mentorship mentorship =
                mentorshipRepository.findById(id).orElse(null);

        if (mentorship == null) {
            return "Request Not Found";
        }

        mentorship.setStatus(status);

        mentorshipRepository.save(mentorship);

        return "Status Updated Successfully";
    }
    @PutMapping("/read/{id}")
    public String markAsRead(@PathVariable Long id) {

        Mentorship mentorship =
                mentorshipRepository.findById(id).orElse(null);

        if (mentorship == null) {
            return "Not Found";
        }

        mentorship.setRead(true);

        mentorshipRepository.save(mentorship);

        return "Updated";
    }

    @PutMapping("/meeting/{id}")
    public String updateMeeting(
            @PathVariable Long id,
            @RequestBody Mentorship meeting) {

        Mentorship mentorship = mentorshipRepository.findById(id).orElse(null);

        if (mentorship == null) {
            return "Request Not Found";
        }

        mentorship.setMeetingDate(meeting.getMeetingDate());
        mentorship.setMeetingTime(meeting.getMeetingTime());
        mentorship.setMeetingLink(meeting.getMeetingLink());

        mentorshipRepository.save(mentorship);

        return "Meeting Details Saved Successfully";
    }
}

