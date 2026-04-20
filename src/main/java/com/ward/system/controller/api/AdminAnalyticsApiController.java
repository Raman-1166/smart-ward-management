package com.ward.system.controller.api;

import com.ward.system.model.Status;
import com.ward.system.model.Ward;
import com.ward.system.service.ComplaintService;
import com.ward.system.service.FeedbackService;
import com.ward.system.service.UserService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/analytics")
public class AdminAnalyticsApiController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private WardService wardService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Map<String, Object> getAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        List<com.ward.system.model.Complaint> allComplaints = complaintService.getAllComplaintsList();
        List<Ward> wards = wardService.getAllWardsList();

        analytics.put("totalUsers", userService.getAllUsers().size());
        analytics.put("totalComplaints", allComplaints.size());
        analytics.put("pendingComplaints", allComplaints.stream().filter(c -> c.getStatus() == Status.PENDING).count());
        analytics.put("resolvedComplaints", allComplaints.stream().filter(c -> c.getStatus() == Status.RESOLVED).count());
        analytics.put("totalFeedback", feedbackService.getAllFeedback().size());

        // Complaints per Ward
        List<Map<String, Object>> complaintsPerWard = new ArrayList<>();
        Map<Long, Long> complaintCounts = allComplaints.stream()
                .collect(Collectors.groupingBy(c -> c.getWard().getId(), Collectors.counting()));
        
        for (Ward ward : wards) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("wardName", ward.getName());
            entry.put("count", complaintCounts.getOrDefault(ward.getId(), 0L));
            complaintsPerWard.add(entry);
        }
        analytics.put("complaintsPerWard", complaintsPerWard);

        // Average Ratings per Ward
        List<Map<String, Object>> avgRatingPerWard = new ArrayList<>();
        for (Ward ward : wards) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("wardName", ward.getName());
            entry.put("avgRating", feedbackService.getAverageRatingForWard(ward.getId()));
            avgRatingPerWard.add(entry);
        }
        analytics.put("avgRatingPerWard", avgRatingPerWard);

        return analytics;
    }
}
