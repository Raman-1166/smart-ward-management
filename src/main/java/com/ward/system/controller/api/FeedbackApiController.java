package com.ward.system.controller.api;

import com.ward.system.model.Feedback;
import com.ward.system.security.CustomUserDetails;
import com.ward.system.service.FeedbackService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackApiController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private WardService wardService;

    @PostMapping
    public ResponseEntity<Feedback> submitFeedback(@RequestBody Feedback feedback,
                                                   @RequestParam Long wardId,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        feedback.setUser(userDetails.getUser());
        feedback.setWard(wardService.getWardById(wardId));
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.submitFeedback(feedback));
    }
}
