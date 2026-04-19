package com.ward.system.controller.api;

import com.ward.system.model.Complaint;
import com.ward.system.model.Status;
import com.ward.system.model.User;
import com.ward.system.security.CustomUserDetails;
import com.ward.system.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintApiController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public Complaint submitComplaint(@RequestBody Complaint complaint, @AuthenticationPrincipal CustomUserDetails userDetails) {
        complaint.setUser(userDetails.getUser());
        return complaintService.submitComplaint(complaint);
    }

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @GetMapping("/my")
    public List<Complaint> getMyComplaints(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return complaintService.getComplaintsByUser(userDetails.getUser().getId());
    }

    @PutMapping("/{id}/status")
    public Complaint updateStatus(@PathVariable Long id, @RequestParam Status status) {
        return complaintService.updateStatus(id, status);
    }
}
