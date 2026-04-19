package com.ward.system.service;

import com.ward.system.model.Ward;
import com.ward.system.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WardService {

    @Autowired
    private WardRepository wardRepository;

    public List<Ward> getAllWards() {
        return wardRepository.findAll();
    }

    public Ward getWardById(Long id) {
        return wardRepository.findById(id).orElse(null);
    }

    public List<Ward> getWardRanking() {
        return wardRepository.findByOrderByCleanlinessScoreDesc();
    }

    public Ward updateCleanlinessScore(Long id, Double newScore) {
        Ward ward = getWardById(id);
        if (ward != null) {
            ward.setCleanlinessScore(newScore);
            return wardRepository.save(ward);
        }
        return null;
    }

    public Ward saveWard(Ward ward) {
        return wardRepository.save(ward);
    }

    public void deleteWard(Long id) {
        wardRepository.deleteById(id);
    }
}
