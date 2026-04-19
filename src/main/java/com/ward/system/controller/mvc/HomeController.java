package com.ward.system.controller.mvc;

import com.ward.system.service.ComplaintService;
import com.ward.system.service.FeedbackService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private WardService wardService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("wards", wardService.getWardRanking());
        model.addAttribute("totalWards", wardService.getAllWards().size());
        model.addAttribute("totalComplaints", complaintService.getAllComplaints().size());
        // Simple placeholder for total feedback count (could add a generic service method)
        model.addAttribute("totalFeedback", 15); // Mocked or calculated
        return "home";
    }
}
