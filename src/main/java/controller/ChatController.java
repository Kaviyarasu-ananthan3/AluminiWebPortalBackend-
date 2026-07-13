package com.alumni.alumniconnectportal.controller;

import com.alumni.alumniconnectportal.model.Chat;
import com.alumni.alumniconnectportal.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @PostMapping("/send")
    public String sendMessage(@RequestBody Chat chat) {

        chat.setDateTime(LocalDateTime.now());

        chatRepository.save(chat);

        return "Message Sent Successfully";
    }

    @GetMapping("/{studentName}/{mentorName}")
    public List<Chat> getMessages(
            @PathVariable String studentName,
            @PathVariable String mentorName) {

        return chatRepository
                .findByStudentNameAndMentorNameOrderByDateTimeAsc(
                        studentName,
                        mentorName
                );
    }

}