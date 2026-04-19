package com.ward.system.repository;

import com.ward.system.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByWardId(Long wardId);

    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.ward.id = :wardId")
    Double findAverageRatingByWardId(@Param("wardId") Long wardId);
}
