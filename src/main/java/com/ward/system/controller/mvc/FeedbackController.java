package com.ward.system.controller.mvc;

import com.ward.system.model.Feedback;
import com.ward.system.model.Ward;
import com.ward.system.security.CustomUserDetails;
import com.ward.system.service.FeedbackService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private WardService wardService;

    @GetMapping("/new")
    public String feedbackForm(Model model) {
        model.addAttribute("feedback", new Feedback());
        model.addAttribute("wards", wardService.getAllWardsList());
        return "feedback-form";
    }

    @PostMapping("/submit")
    public String submitFeedback(@ModelAttribute Feedback feedback,
                                 @RequestParam Long wardId,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Resolve the full Ward entity to avoid partial-entity persistence issues
        Ward ward = wardService.getWardById(wardId);
        feedback.setWard(ward);
        feedback.setUser(userDetails.getUser());
        feedbackService.submitFeedback(feedback);
        return "redirect:/wards/" + ward.getId();
    }
}
