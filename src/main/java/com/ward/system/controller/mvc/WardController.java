package com.ward.system.controller.mvc;

import com.ward.system.model.Ward;
import com.ward.system.service.FeedbackService;
import com.ward.system.service.ServiceDirectoryService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wards")
public class WardController {

    @Autowired
    private WardService wardService;

    @Autowired
    private ServiceDirectoryService serviceDirectoryService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public String listWards(Model model) {
        model.addAttribute("wards", wardService.getAllWards());
        return "wards";
    }

    @GetMapping("/{id}")
    public String wardDetail(@PathVariable Long id, Model model) {
        Ward ward = wardService.getWardById(id);
        if (ward == null) {
            return "redirect:/wards";
        }
        model.addAttribute("ward", ward);
        model.addAttribute("services", serviceDirectoryService.getServicesByWard(id));
        model.addAttribute("feedbackList", feedbackService.getFeedbackByWard(id));
        model.addAttribute("averageRating", feedbackService.getAverageRatingForWard(id));
        return "ward-detail";
    }
}
