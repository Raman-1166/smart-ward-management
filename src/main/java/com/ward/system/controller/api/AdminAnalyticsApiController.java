package com.ward.system.controller.api;

import com.ward.system.model.Status;
import com.ward.system.model.Ward;
import com.ward.system.service.ComplaintService;
import com.ward.system.service.FeedbackService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public Map<String, Object> getAnalytics() {
        Map<String, Object> analytics = new HashMap<>();

        // Complaint counts by status
        Map<Status, Long> complaintStats = complaintService.getAllComplaints().stream()
                .collect(Collectors.groupingBy(c -> c.getStatus(), Collectors.counting()));
        analytics.put("complaintsByStatus", complaintStats);

        // Average ratings per ward
        List<Ward> wards = wardService.getAllWards();
        Map<String, Double> wardRatings = wards.stream()
                .collect(Collectors.toMap(
                        w -> w.getName(),
                        w -> feedbackService.getAverageRatingForWard(w.getId())
                ));
        analytics.put("wardAverageRatings", wardRatings);

        return analytics;
    }
}
