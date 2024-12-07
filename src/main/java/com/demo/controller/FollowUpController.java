package com.demo.controller;

import com.demo.service.FollowUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/followup")
public class FollowUpController {
    @Autowired
    private FollowUpService followUpService;

    @PostMapping("/send")
    public ResponseEntity<String> sendFollowUp(@RequestParam Long patientId) {
        followUpService.sendFollowUpNotification(patientId);
        return ResponseEntity.ok("Follow-up sent");
    }

    @PostMapping("/feedback")
    public ResponseEntity<String> recordFeedback(@RequestParam Long patientId, @RequestParam String feedback) {
        followUpService.recordPatientFeedback(patientId, feedback);
        return ResponseEntity.ok("Feedback recorded");
    }
}