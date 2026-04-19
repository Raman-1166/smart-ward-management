package com.ward.system.controller.mvc;

import com.ward.system.model.Complaint;
import com.ward.system.security.CustomUserDetails;
import com.ward.system.service.ComplaintService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private WardService wardService;

    @GetMapping("/new")
    public String complaintForm(Model model) {
        model.addAttribute("complaint", new Complaint());
        model.addAttribute("wards", wardService.getAllWards());
        return "complaint-form";
    }

    @PostMapping("/submit")
    public String submitComplaint(@ModelAttribute Complaint complaint, @AuthenticationPrincipal CustomUserDetails userDetails) {
        complaint.setUser(userDetails.getUser());
        complaintService.submitComplaint(complaint);
        return "redirect:/complaint/my";
    }

    @GetMapping("/my")
    public String myComplaints(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("complaints", complaintService.getComplaintsByUser(userDetails.getUser().getId()));
        return "my-complaints";
    }
}
