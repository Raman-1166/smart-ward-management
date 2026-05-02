package com.ward.system.controller;

import com.ward.system.model.SystemConfig;
import com.ward.system.repository.SystemConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
public class SystemConfigController {

    @Autowired
    private SystemConfigRepository configRepository;

    @GetMapping
    public ResponseEntity<SystemConfig> getConfig() {
        SystemConfig config = configRepository.findFirstByOrderByIdAsc()
                .orElseGet(() -> {
                    SystemConfig defaultConfig = SystemConfig.builder()
                            .minDeposit(500.0)
                            .serviceFee(5.0)
                            .dailySpendLimit(50.0)
                            .requestIncrement(50.0)
                            .build();
                    return configRepository.save(defaultConfig);
                });
        return ResponseEntity.ok(config);
    }
}
