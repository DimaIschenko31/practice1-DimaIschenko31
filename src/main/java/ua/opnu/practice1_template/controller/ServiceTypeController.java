package ua.opnu.practice1_template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.dto.PopularServiceDto;
import ua.opnu.practice1_template.dto.ServiceTypeDto;
import ua.opnu.practice1_template.service.ServiceTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/service-types")
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @Autowired
    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @PostMapping
    public ResponseEntity<ServiceTypeDto> createServiceType(@RequestBody ServiceTypeDto serviceTypeDto) {
        ServiceTypeDto createdServiceType = serviceTypeService.createServiceType(serviceTypeDto);
        return new ResponseEntity<>(createdServiceType, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceTypeDto>> getAllServiceTypes() {
        List<ServiceTypeDto> serviceTypes = serviceTypeService.getAllServiceTypes();
        return ResponseEntity.ok(serviceTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTypeDto> getServiceTypeById(@PathVariable Long id) {
        ServiceTypeDto serviceType = serviceTypeService.getServiceTypeById(id);
        return ResponseEntity.ok(serviceType);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<PopularServiceDto>> getMostPopularServices() {
        List<PopularServiceDto> popularServices = serviceTypeService.getMostPopularServices();
        return ResponseEntity.ok(popularServices);
    }
}