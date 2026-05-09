package com.ward.system.service;

import com.ward.system.exception.ResourceNotFoundException;
import com.ward.system.model.Ward;
import com.ward.system.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WardService {

    @Autowired
    private WardRepository wardRepository;

    public Page<Ward> getAllWards(Pageable pageable) {
        return wardRepository.findAll(pageable);
    }

    public List<Ward> getAllWardsList() {
        return wardRepository.findAll();
    }

    public Ward getWardById(Long id) {
        return wardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ward", id));
    }

    public List<Ward> getWardRanking() {
        return wardRepository.findByOrderByCleanlinessScoreDesc();
    }

    @Transactional
    public Ward updateCleanlinessScore(Long id, Double newScore) {
        Ward ward = getWardById(id);
        ward.setCleanlinessScore(newScore);
        return wardRepository.save(ward);
    }

    @Transactional
    public Ward saveWard(Ward ward) {
        return wardRepository.save(ward);
    }

    @Transactional
    public void deleteWard(Long id) {
        if (!wardRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ward", id);
        }
        wardRepository.deleteById(id);
    }
}
