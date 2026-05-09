package com.ward.system.service;

import com.ward.system.exception.ResourceNotFoundException;
import com.ward.system.model.Category;
import com.ward.system.model.ServiceEntity;
import com.ward.system.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceDirectoryService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<ServiceEntity> getServicesByWard(Long wardId) {
        return serviceRepository.findByWardId(wardId);
    }

    public List<ServiceEntity> getServicesByWardAndCategory(Long wardId, Category category) {
        return serviceRepository.findByWardIdAndCategory(wardId, category);
    }

    @Transactional
    public ServiceEntity saveService(ServiceEntity serviceEntity) {
        return serviceRepository.save(serviceEntity);
    }

    public ServiceEntity getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service", id));
    }

    @Transactional
    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Service", id);
        }
        serviceRepository.deleteById(id);
    }
}
