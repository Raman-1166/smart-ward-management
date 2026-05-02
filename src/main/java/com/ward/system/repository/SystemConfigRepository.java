package com.ward.system.repository;

import com.ward.system.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    // We only ever have one config record
    Optional<SystemConfig> findFirstByOrderByIdAsc();
}
