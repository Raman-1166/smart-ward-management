package com.ward.system.repository;

import com.ward.system.model.Complaint;
import com.ward.system.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStatus(Status status);
    List<Complaint> findByWardId(Long wardId);
    List<Complaint> findByUserId(Long userId);
}
