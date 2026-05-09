package com.ward.system.controller.api;

import com.ward.system.model.Category;
import com.ward.system.model.ServiceEntity;
import com.ward.system.model.Ward;
import com.ward.system.service.FeedbackService;
import com.ward.system.service.ServiceDirectoryService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wards")
public class WardApiController {

    @Autowired
    private WardService wardService;

    @Autowired
    private ServiceDirectoryService serviceDirectoryService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public List<Ward> getAllWards() {
        return wardService.getAllWardsList();
    }

    @GetMapping("/ranking")
    public List<Ward> getWardRanking() {
        return wardService.getWardRanking();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ward> getWardById(@PathVariable Long id) {
        // getWardById throws ResourceNotFoundException (→ 404) if not found
        return ResponseEntity.ok(wardService.getWardById(id));
    }

    @GetMapping("/{id}/services")
    public List<ServiceEntity> getWardServices(@PathVariable Long id,
                                               @RequestParam(required = false) Category category) {
        if (category != null) {
            return serviceDirectoryService.getServicesByWardAndCategory(id, category);
        }
        return serviceDirectoryService.getServicesByWard(id);
    }

    @GetMapping("/{id}/feedback")
    public List<?> getWardFeedback(@PathVariable Long id) {
        return feedbackService.getFeedbackByWard(id);
    }

    @GetMapping("/{id}/rating")
    public ResponseEntity<Double> getWardRating(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getAverageRatingForWard(id));
    }
}
