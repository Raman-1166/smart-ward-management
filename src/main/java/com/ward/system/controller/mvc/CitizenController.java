package com.ward.system.controller.mvc;

import com.ward.system.security.CustomUserDetails;
import com.ward.system.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class CitizenController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public String dashboard(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("complaints",
                complaintService.getComplaintsByUser(userDetails.getUser().getId()));
        return "citizen_dashboard";
    }
}
