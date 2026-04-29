package com.ward.system.service;

import com.ward.system.model.Complaint;
import com.ward.system.model.Status;
import com.ward.system.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public Complaint submitComplaint(Complaint complaint) {
        complaint.setStatus(Status.PENDING);
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getComplaintsByUser(Long userId) {
        return complaintRepository.findByUserId(userId);
    }

    public Page<Complaint> getAllComplaints(Pageable pageable) {
        return complaintRepository.findAll(pageable);
    }

    public Page<Complaint> getComplaintsByStatus(Status status, Pageable pageable) {
        return complaintRepository.findByStatus(status, pageable);
    }

    public List<Complaint> getAllComplaintsList() {
        return complaintRepository.findAll();
    }

    public Complaint updateStatus(Long complaintId, Status newStatus) {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);
        if (complaint != null) {
            complaint.setStatus(newStatus);
            return complaintRepository.save(complaint);
        }
        return null;
    }

    public List<Complaint> filterByStatus(Status status) {
        return complaintRepository.findByStatus(status);
    }
}
