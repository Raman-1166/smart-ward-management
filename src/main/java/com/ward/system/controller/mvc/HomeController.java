package com.ward.system.controller.mvc;

import com.ward.system.model.Ward;
import com.ward.system.service.ComplaintService;
import com.ward.system.service.FeedbackService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        List<Ward> rankedWards = wardService.getWardRanking();

        // Build a map of wardId → feedbackCount for the ranking table
        Map<Long, Long> feedbackCountByWard = new LinkedHashMap<>();
        for (Ward ward : rankedWards) {
            feedbackCountByWard.put(ward.getId(), feedbackService.getFeedbackCountByWard(ward.getId()));
        }

        model.addAttribute("wards", rankedWards);
        model.addAttribute("feedbackCountByWard", feedbackCountByWard);
        model.addAttribute("totalWards", rankedWards.size());
        model.addAttribute("totalComplaints", complaintService.getAllComplaintsList().size());
        model.addAttribute("totalFeedback", feedbackService.getAllFeedback().size());
        return "home";
    }
}
