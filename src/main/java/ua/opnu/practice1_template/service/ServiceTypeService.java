package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.dto.PopularServiceDto;
import ua.opnu.practice1_template.dto.ServiceTypeDto;
import ua.opnu.practice1_template.exception.ResourceNotFoundException;
import ua.opnu.practice1_template.model.ServiceType;
import ua.opnu.practice1_template.repository.ServiceTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    @Autowired
    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public ServiceTypeDto createServiceType(ServiceTypeDto serviceTypeDto) {
        ServiceType serviceType = convertToEntity(serviceTypeDto);
        ServiceType savedServiceType = serviceTypeRepository.save(serviceType);
        return convertToDto(savedServiceType);
    }

    public List<ServiceTypeDto> getAllServiceTypes() {
        return serviceTypeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ServiceTypeDto getServiceTypeById(Long id) {
        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service type not found with id: " + id));
        return convertToDto(serviceType);
    }

    public List<PopularServiceDto> getMostPopularServices() {
        List<Object[]> results = serviceTypeRepository.findMostPopularServices();
        List<PopularServiceDto> popularServices = new ArrayList<>();

        for (Object[] result : results) {
            ServiceType serviceType = (ServiceType) result[0];
            Long count = (Long) result[1];

            popularServices.add(new PopularServiceDto(
                    serviceType.getId(),
                    serviceType.getName(),
                    count
            ));
        }

        return popularServices;
    }

    private ServiceType convertToEntity(ServiceTypeDto serviceTypeDto) {
        ServiceType serviceType = new ServiceType();
        serviceType.setId(serviceTypeDto.getId());
        serviceType.setName(serviceTypeDto.getName());
        serviceType.setStandardPrice(serviceTypeDto.getStandardPrice());
        return serviceType;
    }

    private ServiceTypeDto convertToDto(ServiceType serviceType) {
        return new ServiceTypeDto(
                serviceType.getId(),
                serviceType.getName(),
                serviceType.getStandardPrice()
        );
    }
}