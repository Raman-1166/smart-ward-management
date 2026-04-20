package com.ward.system.service;

import com.ward.system.model.Feedback;
import com.ward.system.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private WardService wardService;

    public Feedback submitFeedback(Feedback feedback) {
        Feedback saved = feedbackRepository.save(feedback);
        
        // Optionally update the ward cleanliness score based on new average rating (0-100 scale logic)
        Double avgRating = getAverageRatingForWard(feedback.getWard().getId());
        if (avgRating != null) {
            wardService.updateCleanlinessScore(feedback.getWard().getId(), avgRating * 20.0); // 5 stars = 100
        }
        
        return saved;
    }

    public Double getAverageRatingForWard(Long wardId) {
        Double avg = feedbackRepository.findAverageRatingByWardId(wardId);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }

    public List<Feedback> getFeedbackByWard(Long wardId) {
        return feedbackRepository.findByWardId(wardId);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
}
