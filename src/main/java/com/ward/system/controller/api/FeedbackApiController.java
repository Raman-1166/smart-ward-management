package com.ward.system.controller.api;

import com.ward.system.model.Feedback;
import com.ward.system.security.CustomUserDetails;
import com.ward.system.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackApiController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public Feedback submitFeedback(@RequestBody Feedback feedback, @AuthenticationPrincipal CustomUserDetails userDetails) {
        feedback.setUser(userDetails.getUser());
        return feedbackService.submitFeedback(feedback);
    }
}
