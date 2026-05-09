package com.ward.system.service;

import com.ward.system.model.Feedback;
import com.ward.system.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private WardService wardService;

    /**
     * Saves feedback and atomically updates the ward's cleanliness score.
     * Both writes are wrapped in a single transaction — if the score update
     * fails, the feedback save is also rolled back.
     */
    @Transactional
    public Feedback submitFeedback(Feedback feedback) {
        Feedback saved = feedbackRepository.save(feedback);

        // Recalculate average and update ward cleanliness score (5-star → 100 scale)
        Double avgRating = feedbackRepository.findAverageRatingByWardId(feedback.getWard().getId());
        if (avgRating != null) {
            wardService.updateCleanlinessScore(feedback.getWard().getId(), avgRating * 20.0);
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

    public List<Feedback> getFeedbackByUser(Long userId) {
        return feedbackRepository.findByUserId(userId);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public long getFeedbackCountByWard(Long wardId) {
        return feedbackRepository.countByWardId(wardId);
    }
}
