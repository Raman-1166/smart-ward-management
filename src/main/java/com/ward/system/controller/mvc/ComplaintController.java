package com.ward.system.controller.mvc;

import com.ward.system.model.Category;
import com.ward.system.model.Complaint;
import com.ward.system.model.Ward;
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
        model.addAttribute("wards", wardService.getAllWardsList());
        model.addAttribute("categories", Category.values());
        return "complaint-form";
    }

    @PostMapping("/submit")
    public String submitComplaint(@ModelAttribute Complaint complaint,
                                  @RequestParam Long wardId,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Resolve the full Ward entity to avoid partial-entity persistence issues
        Ward ward = wardService.getWardById(wardId);
        complaint.setWard(ward);
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
