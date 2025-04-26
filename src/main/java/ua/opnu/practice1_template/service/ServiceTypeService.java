package ua.opnu.practice1_template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.ServiceType;
import ua.opnu.practice1_template.repository.ServiceTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTypeService {
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceType createServiceType(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    public ServiceType getServiceTypeById(Long id) {
        return serviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service type not found with id: " + id));
    }

    public List<ServiceType> getMostPopularServiceTypes() {
        return serviceTypeRepository.findMostPopularServiceTypes();
    }
}