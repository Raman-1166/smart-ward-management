package com.ward.system.repository;

import com.ward.system.model.Category;
import com.ward.system.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByWardId(Long wardId);
    List<ServiceEntity> findByWardIdAndCategory(Long wardId, Category category);
}
