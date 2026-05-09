package com.ward.system.controller.api;

import com.ward.system.model.Complaint;
import com.ward.system.model.Status;
import com.ward.system.security.CustomUserDetails;
import com.ward.system.service.ComplaintService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintApiController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private WardService wardService;

    /** Submit a new complaint — any authenticated user */
    @PostMapping
    public ResponseEntity<Complaint> submitComplaint(@RequestBody Complaint complaint,
                                                     @RequestParam Long wardId,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        complaint.setUser(userDetails.getUser());
        complaint.setWard(wardService.getWardById(wardId));
        return ResponseEntity.status(HttpStatus.CREATED).body(complaintService.submitComplaint(complaint));
    }

    /** Get all complaints — ADMIN only */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaintsList();
    }

    /** Get the current user's own complaints */
    @GetMapping("/my")
    public List<Complaint> getMyComplaints(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return complaintService.getComplaintsByUser(userDetails.getUser().getId());
    }

    /** Get a single complaint by ID — ADMIN only */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.getComplaintById(id));
    }

    /** Update complaint status — ADMIN only */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Complaint> updateStatus(@PathVariable Long id, @RequestParam Status status) {
        return ResponseEntity.ok(complaintService.updateStatus(id, status));
    }
}
